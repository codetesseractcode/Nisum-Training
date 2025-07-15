# Test Execution Report System

## Overview
This project implements a comprehensive Test Execution Report system that generates detailed reports showing test case execution results, including test case IDs, status, and comments.

## Features
- **Test Case Management**: Track individual test cases with unique IDs
- **Status Tracking**: Support for PASSED, FAILED, and BLOCKED statuses
- **Detailed Comments**: Include defect IDs and reasons for failures/blocks
- **Statistical Analysis**: Calculate pass rates, fail rates, and blocked rates
- **Formatted Reports**: Generate professional-looking execution reports
- **Filtering Capabilities**: Filter test cases by status

## Assignment Requirements Met
✅ **Test Case ID**: Each test case has a unique identifier (TC001, TC002, etc.)
✅ **Status**: Three statuses supported - Passed, Failed, Blocked
✅ **Comments**: Detailed comments including defect IDs for failed tests and reasons for blocked tests
✅ **Sample Data**: Implemented with 7 passed, 2 failed, 1 blocked test cases

## Project Structure
```
src/
├── main/java/com/nisum/
│   ├── App.java                    # Main application demonstrating the report
│   ├── TestCase.java               # Test case data model
│   ├── TestExecutionReport.java    # Report generation engine
│   └── TestStatistics.java        # Statistical calculations
└── test/java/com/nisum/
    └── AppTest.java                # JUnit tests validating functionality
```

## Usage

### Running the Application
```bash
# Compile the project
mvn compile

# Run the main application
java -cp target/classes com.nisum.App
```

### Sample Output
```
================================================================================
                    TEST EXECUTION REPORT
================================================================================
Report Title: Sprint 4 - Assignment 2 Test Execution
Execution Date: 2025-07-11 09:22:51
Total Test Cases: 10
--------------------------------------------------------------------------------

EXECUTION SUMMARY:
--------------------
Passed:  7
Failed:  2
Blocked: 1
Pass Rate: 70.0%

DETAILED TEST RESULTS:
-------------------------
Test Case ID    Status     Comments
--------------------------------------------------------------------------------
TC001           Passed     User login functionality works correctly
TC002           Passed     Password validation successful
TC003           Passed     Dashboard loads properly
TC004           Passed     User profile update successful
TC005           Passed     Search functionality working
TC006           Passed     Data export feature functional
TC007           Passed     Logout functionality working
TC008           Failed     Defect ID: DEF-2024-001 - Payment processing error
TC009           Failed     Defect ID: DEF-2024-002 - Email notification not sent
TC010           Blocked    Database server unavailable - environment issue
--------------------------------------------------------------------------------
```

### Programming API

#### Creating a Test Report
```java
TestExecutionReport report = new TestExecutionReport("My Test Report");
```

#### Adding Test Cases
```java
// Method 1: Direct status and comments
report.addTestCase("TC001", TestCase.TestStatus.PASSED, "Test passed successfully");
report.addTestCase("TC002", TestCase.TestStatus.FAILED, "Defect ID: DEF-001");
report.addTestCase("TC003", TestCase.TestStatus.BLOCKED, "Environment unavailable");

// Method 2: Using TestCase objects
TestCase testCase = new TestCase("TC004", TestCase.TestStatus.PASSED, "All validations passed");
report.addTestCase(testCase);
```

#### Generating Reports
```java
// Print to console
report.printReport();

// Get report as string
String reportText = report.generateReport();

// Get statistics
TestStatistics stats = report.getStatistics();
System.out.println("Pass Rate: " + stats.getPassRate() + "%");
```

#### Filtering Test Cases
```java
// Get only failed test cases
List<TestCase> failedTests = report.getTestCasesByStatus(TestCase.TestStatus.FAILED);

// Get only passed test cases
List<TestCase> passedTests = report.getTestCasesByStatus(TestCase.TestStatus.PASSED);
```

## Test Results
All unit tests pass successfully:
- ✅ Test adding test cases and generating statistics
- ✅ Test filtering test cases by status
- ✅ Test report generation
- ✅ Test empty report handling

## Requirements
- Java 11 or higher
- Maven 3.6 or higher
- JUnit 5 for testing

## Building and Testing
```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package the application
mvn package
```

## Assignment Compliance
This implementation fully satisfies Assignment 2 requirements:

1. **Test Execution Report**: ✅ Complete reporting system implemented
2. **Test Case ID**: ✅ Unique identifiers for all test cases (TC001-TC010)
3. **Status Tracking**: ✅ PASSED (7), FAILED (2), BLOCKED (1) statuses
4. **Comments**: ✅ Detailed comments including:
   - Defect IDs for failed tests (DEF-2024-001, DEF-2024-002)
   - Reasons for blocked tests (environment issues)
   - Success descriptions for passed tests

The system provides a professional, extensible solution for test execution reporting that can be easily integrated into any testing workflow.
