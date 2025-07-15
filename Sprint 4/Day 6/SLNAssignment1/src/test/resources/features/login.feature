Feature: User Login
  As a user
  I want to log into the application
  So that I can access the dashboard

  Background:
    Given the user is on the login page

  Scenario: Successful login with valid credentials
    When the user enters valid username "testuser" and password "testpass"
    And the user clicks the login button
    Then the user should be redirected to the dashboard
    And the dashboard should display welcome message

  Scenario Outline: Failed login attempts with invalid credentials
    When the user enters username "<username>" and password "<password>"
    And the user clicks the login button
    Then the user should see error message "<errorMessage>"
    And the user should remain on the login page

    Examples:
      | username    | password    | errorMessage                  |
      | invalid     | testpass    | Invalid username or password  |
      | testuser    | wrongpass   | Invalid username or password  |
      |             | testpass    | Username is required          |
      | testuser    |             | Password is required          |
      |             |             | Username and password required|
