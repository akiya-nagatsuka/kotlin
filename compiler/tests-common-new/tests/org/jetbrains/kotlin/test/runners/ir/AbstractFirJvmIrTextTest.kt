/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.test.runners.ir

import org.jetbrains.kotlin.test.Constructor
import org.jetbrains.kotlin.test.FirParser
import org.jetbrains.kotlin.test.TargetBackend
import org.jetbrains.kotlin.test.backend.handlers.NoFir2IrCompilationErrorsHandler
import org.jetbrains.kotlin.test.backend.ir.IrBackendInput
import org.jetbrains.kotlin.test.backend.ir.IrConstCheckerHandler
import org.jetbrains.kotlin.test.builders.*
import org.jetbrains.kotlin.test.directives.CodegenTestDirectives
import org.jetbrains.kotlin.test.directives.CodegenTestDirectives.DUMP_IR
import org.jetbrains.kotlin.test.directives.CodegenTestDirectives.DUMP_KT_IR
import org.jetbrains.kotlin.test.directives.CodegenTestDirectives.DUMP_SIGNATURES
import org.jetbrains.kotlin.test.directives.DiagnosticsDirectives.DIAGNOSTICS
import org.jetbrains.kotlin.test.directives.LanguageSettingsDirectives
import org.jetbrains.kotlin.test.frontend.fir.Fir2IrResultsConverter
import org.jetbrains.kotlin.test.frontend.fir.FirFrontendFacade
import org.jetbrains.kotlin.test.frontend.fir.FirOutputArtifact
import org.jetbrains.kotlin.test.model.*
import org.jetbrains.kotlin.test.runners.*
import org.jetbrains.kotlin.test.runners.codegen.FirPsiCodegenTest
import org.jetbrains.kotlin.test.runners.ir.AbstractIrTextTest.KlibFacades
import org.jetbrains.kotlin.test.services.KotlinTestInfo
import org.jetbrains.kotlin.test.services.fir.FirOldFrontendMetaConfigurator

abstract class AbstractFirJvmIrTextTest(
    protected val parser: FirParser,
) : AbstractJvmIrTextTest<FirOutputArtifact>() {
    override val frontend: FrontendKind<*>
        get() = FrontendKinds.FIR
    override val frontendFacade: Constructor<FrontendFacade<FirOutputArtifact>>
        get() = ::FirFrontendFacade
    override val converter: Constructor<Frontend2BackendConverter<FirOutputArtifact, IrBackendInput>>
        get() = ::Fir2IrResultsConverter

    override fun configure(builder: TestConfigurationBuilder) {
        super.configure(builder)
        builder.commonConfigurationForK2(parser)
    }
}

open class AbstractFirLightTreeJvmIrTextTest : AbstractFirJvmIrTextTest(FirParser.LightTree)

@FirPsiCodegenTest
open class AbstractFirPsiJvmIrTextTest : AbstractFirJvmIrTextTest(FirParser.Psi)

fun TestConfigurationBuilder.configureTieredFir2IrJvmTest(
    parser: FirParser,
    testInfo: KotlinTestInfo,
    targetBackend: TargetBackend,
    converter: Constructor<Frontend2BackendConverter<FirOutputArtifact, IrBackendInput>>,
    klibFacades: KlibFacades?,
) {
    // See: compiler/testData/diagnostics/tests/multiplatform/actualAnnotationsNotMatchExpect/checkDiagnosticFullText.kt
    // It expects `+MultiPlatformProjects` to be present a priori because of its location.

    // Also, it's important to configure the same handlers, otherwise differences with the `.fir.kt` files
    // (the absence of diagnostics) would be considered as FIR tier failure.

    configureTieredFirJvmTest(parser)

    this.testInfo = testInfo
    configureKotlinCompilerWIthTargetBackendTest(targetBackend)

    configureAbstractIrTextSettings(targetBackend, converter, klibFacades, includeAllDumpHandlers = false)

    defaultDirectives {
        // In the future we'll probably want to preserve all dumps from lower levels,
        // but for now I'd like to avoid clashing test data files.
//                +FIR_DUMP
        -DUMP_IR
        -DUMP_KT_IR
        -DUMP_SIGNATURES

        // Otherwise, warnings will be suppressed, but we need to render them for FIR tier dumps
        -DIAGNOSTICS
    }

    // Otherwise, GlobalMetadataInfoHandler may want to write differences to the K1 test data file, not K2
    useMetaTestConfigurators(::FirOldFrontendMetaConfigurator)

    // Needed for `compiler/testData/diagnostics/tests/modifiers/const/kotlinJavaCycle.kt`.
    configureIrHandlersStep {
        useHandlers(
            ::IrConstCheckerHandler,
        )
    }

    forTestsMatching("compiler/testData/ir/irText/properties/backingField/*") {
        defaultDirectives {
            LanguageSettingsDirectives.LANGUAGE with "+ExplicitBackingFields"
        }
    }

    // See: `AbstractFirWithActualizerDiagnosticsTest`
    forTestsMatching("diagnostics/tests/multiplatform/*") {
        defaultDirectives {
            +CodegenTestDirectives.IGNORE_FIR2IR_EXCEPTIONS_IF_FIR_CONTAINS_ERRORS
        }

        // It's important to avoid adding `PlatformModuleProvider` when running `testWithJvmBackend` that
        // include `// IGNORE_FIR_DIAGNOSTICS` as then we'll get a new empty module that doesn't emit
        // any errors, but `NoFirCompilationErrorsHandler` will complain.
        // It's probably better to make the diagnostic a module one instead of global and update the
        // test data, but I'm a bit tired of fighting corner cases and don't want to do it now.
        configureIrActualizerDiagnosticsTest()
    }

    // It's important that this handler goes after `IrDiagnosticsHandler`,
    // otherwise `GlobalMetadataInfoHandler` won't include IR diagnostics, and
    // we'll see test differences.
    // See: `compiler/testData/diagnostics/tests/multiplatform/actualAnnotationsNotMatchExpect/typeParameters.kt`.
    forTestsMatching("*") {
        configureIrHandlersStep {
            useHandlers(::NoFir2IrCompilationErrorsHandler)
        }
    }
}

abstract class AbstractTieredFir2IrJvmTest(parser: FirParser) : AbstractFirJvmIrTextTest(parser) {
    override fun configure(builder: TestConfigurationBuilder) {
        // See: compiler/testData/diagnostics/tests/multiplatform/actualAnnotationsNotMatchExpect/checkDiagnosticFullText.kt
        // It expects `+MultiPlatformProjects` to be present a priori because of its location.
        // Also, it's important to configure the same handlers, otherwise differences with the `.fir.kt` files
        // (the absence of diagnostics) would be considered as FIR tier failure.

        builder.configureTieredFir2IrJvmTest(parser, testInfo, targetBackend, converter, klibFacades)

        builder.useAfterAnalysisCheckers(
            { TestTierChecker(TestTiers.FIR2IR, it) },
        )
    }
}

open class AbstractTieredFir2IrJvmLightTreeTest : AbstractTieredFir2IrJvmTest(FirParser.LightTree)
