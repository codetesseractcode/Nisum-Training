package com.nisum;

/**
 * Represents a test case with its execution details
 */
public class TestCase {
    private String testCaseId;
    private TestStatus status;
    private String comments;

    public enum TestStatus {
        PASSED("Passed"),
        FAILED("Failed"),
        BLOCKED("Blocked");

        private final String displayName;

        TestStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public TestCase(String testCaseId, TestStatus status, String comments) {
        this.testCaseId = testCaseId;
        this.status = status;
        this.comments = comments;
    }

    // Getters and setters
    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return String.format("TestCase{id='%s', status=%s, comments='%s'}",
                           testCaseId, status.getDisplayName(), comments);
    }
}
