package com.nisum;

/**
 * Main application to demonstrate Test Execution Report functionality
 */
public class App {
    public static void main(String[] args) {
        // Create a new test execution report
        TestExecutionReport report = new TestExecutionReport("Sprint 4 - Assignment 2 Test Execution");

        // Add test cases based on the assignment requirements:
        // 7 passed, 2 failed, 1 blocked due to environment

        // Add passed test cases
        report.addTestCase("TC001", TestCase.TestStatus.PASSED, "User login functionality works correctly");
        report.addTestCase("TC002", TestCase.TestStatus.PASSED, "Password validation successful");
        report.addTestCase("TC003", TestCase.TestStatus.PASSED, "Dashboard loads properly");
        report.addTestCase("TC004", TestCase.TestStatus.PASSED, "User profile update successful");
        report.addTestCase("TC005", TestCase.TestStatus.PASSED, "Search functionality working");
        report.addTestCase("TC006", TestCase.TestStatus.PASSED, "Data export feature functional");
        report.addTestCase("TC007", TestCase.TestStatus.PASSED, "Logout functionality working");

        // Add failed test cases
        report.addTestCase("TC008", TestCase.TestStatus.FAILED, "Defect ID: DEF-2024-001 - Payment processing error");
        report.addTestCase("TC009", TestCase.TestStatus.FAILED, "Defect ID: DEF-2024-002 - Email notification not sent");

        // Add blocked test case
        report.addTestCase("TC010", TestCase.TestStatus.BLOCKED, "Database server unavailable - environment issue");

        // Generate and display the report
        System.out.println("Generating Test Execution Report...\n");
        report.printReport();

        // Display additional statistics
        TestStatistics stats = report.getStatistics();
        System.out.println("\nAdditional Statistics:");
        System.out.println("Pass Rate: " + String.format("%.1f%%", stats.getPassRate()));
        System.out.println("Fail Rate: " + String.format("%.1f%%", stats.getFailRate()));
        System.out.println("Blocked Rate: " + String.format("%.1f%%", stats.getBlockedRate()));

        // Demonstrate filtering by status
        System.out.println("\n" + "=".repeat(50));
        System.out.println("FAILED TEST CASES DETAILS:");
        System.out.println("=".repeat(50));
        report.getTestCasesByStatus(TestCase.TestStatus.FAILED).forEach(tc -> {
            System.out.println("- " + tc.getTestCaseId() + ": " + tc.getComments());
        });
    }
}
