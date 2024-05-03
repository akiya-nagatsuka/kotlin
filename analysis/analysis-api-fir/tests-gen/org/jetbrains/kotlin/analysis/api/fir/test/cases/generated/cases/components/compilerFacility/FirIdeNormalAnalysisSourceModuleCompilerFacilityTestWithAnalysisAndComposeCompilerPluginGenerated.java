/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.fir.test.cases.generated.cases.components.compilerFacility;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.analysis.api.fir.test.configurators.AnalysisApiFirTestConfiguratorFactory;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiTestConfiguratorFactoryData;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiTestConfigurator;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.TestModuleKind;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.FrontendKind;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisSessionMode;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiMode;
import org.jetbrains.kotlin.analysis.api.impl.base.test.cases.components.compilerFacility.AbstractCompilerFacilityTestWithAnalysisAndComposeCompilerPlugin;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.analysis.api.GenerateAnalysisApiTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("analysis/analysis-api/testData/components/compilerFacility/bugsFromRealComposeApps")
@TestDataPath("$PROJECT_ROOT")
public class FirIdeNormalAnalysisSourceModuleCompilerFacilityTestWithAnalysisAndComposeCompilerPluginGenerated extends AbstractCompilerFacilityTestWithAnalysisAndComposeCompilerPlugin {
  @NotNull
  @Override
  public AnalysisApiTestConfigurator getConfigurator() {
    return AnalysisApiFirTestConfiguratorFactory.INSTANCE.createConfigurator(
      new AnalysisApiTestConfiguratorFactoryData(
        FrontendKind.Fir,
        TestModuleKind.Source,
        AnalysisSessionMode.Normal,
        AnalysisApiMode.Ide
      )
    );
  }

  @Test
  public void testAllFilesPresentInBugsFromRealComposeApps() {
    KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/components/compilerFacility/bugsFromRealComposeApps"), Pattern.compile("^(.+)\\.(kt)$"), null, true);
  }

  @Test
  @TestMetadata("constExprInitializer.kt")
  public void testConstExprInitializer() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/bugsFromRealComposeApps/constExprInitializer.kt");
  }

  @Test
  @TestMetadata("constExprLateInitializer.kt")
  public void testConstExprLateInitializer() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/bugsFromRealComposeApps/constExprLateInitializer.kt");
  }

  @Test
  @TestMetadata("interface.kt")
  public void testInterface() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/bugsFromRealComposeApps/interface.kt");
  }

  @Test
  @TestMetadata("lazyPropertyBackingField.kt")
  public void testLazyPropertyBackingField() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/bugsFromRealComposeApps/lazyPropertyBackingField.kt");
  }

  @Test
  @TestMetadata("propertyWithDelegateBackingField.kt")
  public void testPropertyWithDelegateBackingField() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/bugsFromRealComposeApps/propertyWithDelegateBackingField.kt");
  }
}
