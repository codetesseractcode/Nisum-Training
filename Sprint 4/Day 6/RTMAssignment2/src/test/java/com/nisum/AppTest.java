package com.nisum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Test class for Test Execution Report functionality
 */
public class AppTest {

    private TestExecutionReport report;

    @BeforeEach
    void setUp() {
        report = new TestExecutionReport("Test Report");
    }

    @Test
    @DisplayName("Test adding test cases and generating statistics")
    void testTestExecutionReport() {
        // Add test cases matching assignment requirements
        report.addTestCase("TC001", TestCase.TestStatus.PASSED, "Login successful");
        report.addTestCase("TC002", TestCase.TestStatus.PASSED, "Navigation working");
        report.addTestCase("TC003", TestCase.TestStatus.PASSED, "Data validation passed");
        report.addTestCase("TC004", TestCase.TestStatus.PASSED, "Search functionality working");
        report.addTestCase("TC005", TestCase.TestStatus.PASSED, "User management working");
        report.addTestCase("TC006", TestCase.TestStatus.PASSED, "Report generation successful");
        report.addTestCase("TC007", TestCase.TestStatus.PASSED, "Logout successful");

        report.addTestCase("TC008", TestCase.TestStatus.FAILED, "Defect ID: DEF-001 - Payment error");
        report.addTestCase("TC009", TestCase.TestStatus.FAILED, "Defect ID: DEF-002 - Email error");

        report.addTestCase("TC010", TestCase.TestStatus.BLOCKED, "Environment unavailable");

        // Verify statistics
        TestStatistics stats = report.getStatistics();
        assertEquals(10, stats.getTotalTests());
        assertEquals(7, stats.getPassedTests());
        assertEquals(2, stats.getFailedTests());
        assertEquals(1, stats.getBlockedTests());
        assertEquals(70.0, stats.getPassRate(), 0.1);
    }

    @Test
    @DisplayName("Test filtering test cases by status")
    void testFilteringByStatus() {
        report.addTestCase("TC001", TestCase.TestStatus.PASSED, "Test passed");
        report.addTestCase("TC002", TestCase.TestStatus.FAILED, "Test failed");
        report.addTestCase("TC003", TestCase.TestStatus.BLOCKED, "Test blocked");

        List<TestCase> failedTests = report.getTestCasesByStatus(TestCase.TestStatus.FAILED);
        assertEquals(1, failedTests.size());
        assertEquals("TC002", failedTests.get(0).getTestCaseId());
    }

    @Test
    @DisplayName("Test report generation")
    void testReportGeneration() {
        report.addTestCase("TC001", TestCase.TestStatus.PASSED, "Success");
        report.addTestCase("TC002", TestCase.TestStatus.FAILED, "Defect ID: DEF-001");

        String reportText = report.generateReport();

        assertNotNull(reportText);
        assertTrue(reportText.contains("TEST EXECUTION REPORT"));
        assertTrue(reportText.contains("TC001"));
        assertTrue(reportText.contains("TC002"));
        assertTrue(reportText.contains("Passed"));
        assertTrue(reportText.contains("Failed"));
        assertTrue(reportText.contains("Pass Rate"));
    }

    @Test
    @DisplayName("Test empty report")
    void testEmptyReport() {
        TestStatistics stats = report.getStatistics();
        assertEquals(0, stats.getTotalTests());
        assertEquals(0.0, stats.getPassRate());
    }
}
