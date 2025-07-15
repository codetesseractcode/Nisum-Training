# Requirement Traceability Matrix (RTM)

## Project: RTMAssignment1
## Date: July 11, 2025

## Requirements and Test Case Mapping

| Requirement ID | Requirement Description | Test Case ID | Test Case Description | Test Class | Test Method | Status |
|---------------|------------------------|--------------|----------------------|------------|-------------|---------|
| R1 | User can log in | TC001 | Valid login with correct credentials | LoginTest | testValidLogin | Active |
| R1 | User can log in | TC002 | Invalid login with wrong password | LoginTest | testInvalidLoginWrongPassword | Active |
| R1 | User can log in | TC003 | Invalid login with non-existent user | LoginTest | testInvalidLoginNonExistentUser | Active |
| R2 | User can reset password | TC004 | Valid password reset with correct email | PasswordResetTest | testValidPasswordReset | Active |
| R2 | User can reset password | TC005 | Invalid password reset with wrong email | PasswordResetTest | testInvalidPasswordResetWrongEmail | Active |
| R2 | User can reset password | TC006 | Invalid password reset for non-existent user | PasswordResetTest | testInvalidPasswordResetNonExistentUser | Active |
| R3 | User can update profile | TC007 | Valid profile update for existing user | ProfileUpdateTest | testValidProfileUpdate | Active |
| R3 | User can update profile | TC008 | Invalid profile update for non-existent user | ProfileUpdateTest | testInvalidProfileUpdateNonExistentUser | Active |
| R3 | User can update profile | TC009 | Profile update with empty profile information | ProfileUpdateTest | testProfileUpdateWithEmptyProfile | Active |

## Test Coverage Summary

| Requirement ID | Requirement Description | Number of Test Cases | Coverage |
|---------------|------------------------|---------------------|----------|
| R1 | User can log in | 3 | 100% |
| R2 | User can reset password | 3 | 100% |
| R3 | User can update profile | 3 | 100% |

**Total Requirements:** 3  
**Total Test Cases:** 9  
**Requirements Coverage:** 100%

## Test Case Categories

### Positive Test Cases (Happy Path)
- TC001: Valid login with correct credentials
- TC004: Valid password reset with correct email
- TC007: Valid profile update for existing user
- TC009: Profile update with empty profile information

### Negative Test Cases (Error Scenarios)
- TC002: Invalid login with wrong password
- TC003: Invalid login with non-existent user
- TC005: Invalid password reset with wrong email
- TC006: Invalid password reset for non-existent user
- TC008: Invalid profile update for non-existent user

## Implementation Notes

### SOLID Principles Applied:
1. **Single Responsibility Principle (SRP)**: Each test class focuses on testing one specific requirement
2. **Open/Closed Principle (OCP)**: UserService can be extended without modifying existing code
3. **Interface Segregation Principle (ISP)**: Clear separation of concerns in test methods
4. **Dependency Inversion Principle (DIP)**: Tests depend on abstractions, not concrete implementations

### YAGNI Principle Applied:
- Only implemented the three required functionalities
- No over-engineering or unnecessary features
- Simple, focused implementation that meets the exact requirements

## Test Execution

To run all tests:
```bash
mvn test
```

To run specific test class:
```bash
mvn test -Dtest=LoginTest
mvn test -Dtest=PasswordResetTest
mvn test -Dtest=ProfileUpdateTest
```

## Traceability Matrix Benefits

1. **Requirement Coverage**: Ensures all requirements are tested
2. **Test Case Management**: Easy tracking of test cases per requirement
3. **Impact Analysis**: Helps identify affected tests when requirements change
4. **Quality Assurance**: Provides visibility into testing completeness
5. **Compliance**: Supports audit and regulatory requirements
