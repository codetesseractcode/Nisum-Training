package com.nisum;

/**
 * Holds statistical information about test execution
 */
public class TestStatistics {
    private final int totalTests;
    private final int passedTests;
    private final int failedTests;
    private final int blockedTests;

    public TestStatistics(int totalTests, int passedTests, int failedTests, int blockedTests) {
        this.totalTests = totalTests;
        this.passedTests = passedTests;
        this.failedTests = failedTests;
        this.blockedTests = blockedTests;
    }

    public int getTotalTests() {
        return totalTests;
    }

    public int getPassedTests() {
        return passedTests;
    }

    public int getFailedTests() {
        return failedTests;
    }

    public int getBlockedTests() {
        return blockedTests;
    }

    public double getPassRate() {
        return totalTests == 0 ? 0 : (passedTests * 100.0) / totalTests;
    }

    public double getFailRate() {
        return totalTests == 0 ? 0 : (failedTests * 100.0) / totalTests;
    }

    public double getBlockedRate() {
        return totalTests == 0 ? 0 : (blockedTests * 100.0) / totalTests;
    }

    @Override
    public String toString() {
        return String.format("TestStatistics{total=%d, passed=%d, failed=%d, blocked=%d, passRate=%.1f%%}",
                           totalTests, passedTests, failedTests, blockedTests, getPassRate());
    }
}
