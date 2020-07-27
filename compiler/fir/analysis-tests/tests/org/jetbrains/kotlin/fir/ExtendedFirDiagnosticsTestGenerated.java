/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.test.KotlinTestUtils;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("compiler/fir/analysis-tests/testData/extendedCheckers")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class ExtendedFirDiagnosticsTestGenerated extends AbstractExtendedFirDiagnosticsTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest(this::doTest, this, testDataFilePath);
    }

    public void testAllFilesPresentInExtendedCheckers() throws Exception {
        KotlinTestUtils.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("compiler/fir/analysis-tests/testData/extendedCheckers"), Pattern.compile("^([^.]+)\\.kt$"), null, true);
    }

    @TestMetadata("RedundantExplicitTypeChecker.kt")
    public void testRedundantExplicitTypeChecker() throws Exception {
        runTest("compiler/fir/analysis-tests/testData/extendedCheckers/RedundantExplicitTypeChecker.kt");
    }

    @TestMetadata("RedundantModalityModifierChecker.kt")
    public void testRedundantModalityModifierChecker() throws Exception {
        runTest("compiler/fir/analysis-tests/testData/extendedCheckers/RedundantModalityModifierChecker.kt");
    }

    @TestMetadata("RedundantReturnUnitTypeChecker.kt")
    public void testRedundantReturnUnitTypeChecker() throws Exception {
        runTest("compiler/fir/analysis-tests/testData/extendedCheckers/RedundantReturnUnitTypeChecker.kt");
    }

    @TestMetadata("RedundantSingleExpressionStringTemplateChecker.kt")
    public void testRedundantSingleExpressionStringTemplateChecker() throws Exception {
        runTest("compiler/fir/analysis-tests/testData/extendedCheckers/RedundantSingleExpressionStringTemplateChecker.kt");
    }

    @TestMetadata("RedundantVisibilityModifierChecker.kt")
    public void testRedundantVisibilityModifierChecker() throws Exception {
        runTest("compiler/fir/analysis-tests/testData/extendedCheckers/RedundantVisibilityModifierChecker.kt");
    }

    @TestMetadata("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment")
    @TestDataPath("$PROJECT_ROOT")
    @RunWith(JUnit3RunnerWithInners.class)
    public static class CanBeReplacedWithOperatorAssignment extends AbstractExtendedFirDiagnosticsTest {
        private void runTest(String testDataFilePath) throws Exception {
            KotlinTestUtils.runTest(this::doTest, this, testDataFilePath);
        }

        public void testAllFilesPresentInCanBeReplacedWithOperatorAssignment() throws Exception {
            KotlinTestUtils.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment"), Pattern.compile("^([^.]+)\\.kt$"), null, true);
        }

        @TestMetadata("BasicTest.kt")
        public void testBasicTest() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/BasicTest.kt");
        }

        @TestMetadata("ComplexExpression.kt")
        public void testComplexExpression() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/ComplexExpression.kt");
        }

        @TestMetadata("flexibleTypeBug.kt")
        public void testFlexibleTypeBug() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/flexibleTypeBug.kt");
        }

        @TestMetadata("illegalMultipleOperators.kt")
        public void testIllegalMultipleOperators() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/illegalMultipleOperators.kt");
        }

        @TestMetadata("illegalMultipleOperatorsMiddle.kt")
        public void testIllegalMultipleOperatorsMiddle() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/illegalMultipleOperatorsMiddle.kt");
        }

        @TestMetadata("invalidSubtraction.kt")
        public void testInvalidSubtraction() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/invalidSubtraction.kt");
        }

        @TestMetadata("list.kt")
        public void testList() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/list.kt");
        }

        @TestMetadata("multipleOperators.kt")
        public void testMultipleOperators() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/multipleOperators.kt");
        }

        @TestMetadata("multipleOperatorsRightSideRepeat.kt")
        public void testMultipleOperatorsRightSideRepeat() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/multipleOperatorsRightSideRepeat.kt");
        }

        @TestMetadata("mutableList.kt")
        public void testMutableList() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/mutableList.kt");
        }

        @TestMetadata("nonCommutativeRepeat.kt")
        public void testNonCommutativeRepeat() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/nonCommutativeRepeat.kt");
        }

        @TestMetadata("nonRepeatingAssignment.kt")
        public void testNonRepeatingAssignment() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/nonRepeatingAssignment.kt");
        }

        @TestMetadata("OperatorAssignment.kt")
        public void testOperatorAssignment() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/OperatorAssignment.kt");
        }

        @TestMetadata("plusAssignConflict.kt")
        public void testPlusAssignConflict() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/plusAssignConflict.kt");
        }

        @TestMetadata("rightSideRepeat.kt")
        public void testRightSideRepeat() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/rightSideRepeat.kt");
        }

        @TestMetadata("simpleAssign.kt")
        public void testSimpleAssign() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/simpleAssign.kt");
        }

        @TestMetadata("validAddition.kt")
        public void testValidAddition() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/validAddition.kt");
        }

        @TestMetadata("validSubtraction.kt")
        public void testValidSubtraction() throws Exception {
            runTest("compiler/fir/analysis-tests/testData/extendedCheckers/canBeReplacedWithOperatorAssignment/validSubtraction.kt");
        }
    }
}
