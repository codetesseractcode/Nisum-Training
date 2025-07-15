package com.nisum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Generates and manages test execution reports
 */
public class TestExecutionReport {
    private List<TestCase> testCases;
    private String reportTitle;
    private LocalDateTime executionDateTime;

    public TestExecutionReport(String reportTitle) {
        this.reportTitle = reportTitle;
        this.testCases = new ArrayList<>();
        this.executionDateTime = LocalDateTime.now();
    }

    public void addTestCase(TestCase testCase) {
        this.testCases.add(testCase);
    }

    public void addTestCase(String testCaseId, TestCase.TestStatus status, String comments) {
        this.testCases.add(new TestCase(testCaseId, status, comments));
    }

    /**
     * Generate a formatted test execution report
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();

        // Header
        report.append("=".repeat(80)).append("\n");
        report.append("                    TEST EXECUTION REPORT\n");
        report.append("=".repeat(80)).append("\n");
        report.append("Report Title: ").append(reportTitle).append("\n");
        report.append("Execution Date: ").append(executionDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        report.append("Total Test Cases: ").append(testCases.size()).append("\n");
        report.append("-".repeat(80)).append("\n\n");

        // Summary statistics
        Map<TestCase.TestStatus, Long> statusCounts = testCases.stream()
                .collect(Collectors.groupingBy(TestCase::getStatus, Collectors.counting()));

        report.append("EXECUTION SUMMARY:\n");
        report.append("-".repeat(20)).append("\n");
        report.append(String.format("Passed:  %d\n", statusCounts.getOrDefault(TestCase.TestStatus.PASSED, 0L)));
        report.append(String.format("Failed:  %d\n", statusCounts.getOrDefault(TestCase.TestStatus.FAILED, 0L)));
        report.append(String.format("Blocked: %d\n", statusCounts.getOrDefault(TestCase.TestStatus.BLOCKED, 0L)));

        double passRate = testCases.isEmpty() ? 0 :
                         (statusCounts.getOrDefault(TestCase.TestStatus.PASSED, 0L) * 100.0) / testCases.size();
        report.append(String.format("Pass Rate: %.1f%%\n\n", passRate));

        // Detailed test case results
        report.append("DETAILED TEST RESULTS:\n");
        report.append("-".repeat(25)).append("\n");
        report.append(String.format("%-15s %-10s %-50s\n", "Test Case ID", "Status", "Comments"));
        report.append("-".repeat(80)).append("\n");

        for (TestCase testCase : testCases) {
            report.append(String.format("%-15s %-10s %-50s\n",
                         testCase.getTestCaseId(),
                         testCase.getStatus().getDisplayName(),
                         testCase.getComments()));
        }

        report.append("-".repeat(80)).append("\n");
        report.append("End of Report\n");

        return report.toString();
    }

    /**
     * Print report to console
     */
    public void printReport() {
        System.out.println(generateReport());
    }

    /**
     * Get test cases by status
     */
    public List<TestCase> getTestCasesByStatus(TestCase.TestStatus status) {
        return testCases.stream()
                .filter(tc -> tc.getStatus() == status)
                .collect(Collectors.toList());
    }

    /**
     * Get overall test statistics
     */
    public TestStatistics getStatistics() {
        Map<TestCase.TestStatus, Long> statusCounts = testCases.stream()
                .collect(Collectors.groupingBy(TestCase::getStatus, Collectors.counting()));

        return new TestStatistics(
                testCases.size(),
                statusCounts.getOrDefault(TestCase.TestStatus.PASSED, 0L).intValue(),
                statusCounts.getOrDefault(TestCase.TestStatus.FAILED, 0L).intValue(),
                statusCounts.getOrDefault(TestCase.TestStatus.BLOCKED, 0L).intValue()
        );
    }

    // Getters
    public List<TestCase> getTestCases() {
        return new ArrayList<>(testCases);
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public LocalDateTime getExecutionDateTime() {
        return executionDateTime;
    }
}
