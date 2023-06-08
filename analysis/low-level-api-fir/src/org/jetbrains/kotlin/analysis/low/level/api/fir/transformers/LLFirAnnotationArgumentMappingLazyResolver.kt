/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.low.level.api.fir.transformers

import org.jetbrains.kotlin.analysis.low.level.api.fir.api.targets.LLFirResolveTarget
import org.jetbrains.kotlin.analysis.low.level.api.fir.file.builder.LLFirLockProvider
import org.jetbrains.kotlin.analysis.low.level.api.fir.lazy.resolve.FirLazyBodiesCalculator.calculateAnnotations
import org.jetbrains.kotlin.analysis.low.level.api.fir.lazy.resolve.LLFirPhaseUpdater
import org.jetbrains.kotlin.analysis.low.level.api.fir.util.checkAnnotationArgumentsMappingIsResolved
import org.jetbrains.kotlin.analysis.low.level.api.fir.util.checkPhase
import org.jetbrains.kotlin.fir.*
import org.jetbrains.kotlin.fir.declarations.*
import org.jetbrains.kotlin.fir.expressions.FirAnnotation
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall
import org.jetbrains.kotlin.fir.resolve.ScopeSession
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirTowerDataContextCollector
import org.jetbrains.kotlin.fir.resolve.transformers.plugin.FirAnnotationArgumentsMappingTransformer
import org.jetbrains.kotlin.fir.types.FirTypeProjection

internal object LLFirAnnotationArgumentMappingLazyResolver : LLFirLazyResolver(FirResolvePhase.ANNOTATIONS_ARGUMENTS_MAPPING) {
    override fun resolve(
        target: LLFirResolveTarget,
        lockProvider: LLFirLockProvider,
        session: FirSession,
        scopeSession: ScopeSession,
        towerDataContextCollector: FirTowerDataContextCollector?,
    ) {
        val resolver = LLFirAnnotationArgumentsMappingTargetResolver(target, lockProvider, session, scopeSession)
        resolver.resolveDesignation()
    }

    override fun updatePhaseForDeclarationInternals(target: FirElementWithResolveState) {
        LLFirPhaseUpdater.updateDeclarationInternalsPhase(target, resolverPhase, updateForLocalDeclarations = false)
    }

    override fun checkIsResolved(target: FirElementWithResolveState) {
        target.checkPhase(resolverPhase)
        if (target !is FirAnnotationContainer) return
        for (annotation in target.annotations) {
            if (annotation is FirAnnotationCall) {
                checkAnnotationArgumentsMappingIsResolved(annotation, target)
            }
        }
        checkNestedDeclarationsAreResolved(target)
    }
}

private class LLFirAnnotationArgumentsMappingTargetResolver(
    resolveTarget: LLFirResolveTarget,
    lockProvider: LLFirLockProvider,
    session: FirSession,
    scopeSession: ScopeSession,
) : LLFirAbstractBodyTargetResolver(
    resolveTarget,
    lockProvider,
    scopeSession,
    FirResolvePhase.ANNOTATIONS_ARGUMENTS_MAPPING,
) {
    override val transformer = FirAnnotationArgumentsMappingTransformer(
        session,
        scopeSession,
        resolverPhase,
        returnTypeCalculator = createReturnTypeCalculator(towerDataContextCollector = null)
    )

    override fun doLazyResolveUnderLock(target: FirElementWithResolveState) {
        resolveWithKeeper(target, AnnotationArgumentMappingStateKeepers.DECLARATION, ::calculateAnnotations) {
            transformAnnotations(target)
        }
    }
}

private object AnnotationArgumentMappingStateKeepers : AbstractAnnotationStateKeepers() {
    override val ANNOTATION: StateKeeper<FirAnnotation> = stateKeeper {
        add(ANNOTATION_BASE)
        add(FirAnnotation::argumentMapping, FirAnnotation::replaceArgumentMapping)
        add(FirAnnotation::typeArgumentsCopied, FirAnnotation::replaceTypeArguments)
    }

    val DECLARATION: StateKeeper<FirElementWithResolveState>
        get() = DECLARATION_BASE
}

private val FirAnnotation.typeArgumentsCopied: List<FirTypeProjection>
    get() = if (typeArguments.isEmpty()) emptyList() else ArrayList(typeArguments)