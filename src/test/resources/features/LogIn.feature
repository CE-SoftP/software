Feature: User Login

  Background:
    Given the app is running
    And I am on the login page

  Scenario: Successful login as an admin
    When I enter my admin username "eman" and password "555"
    And I click the "LogInBtn" button
    Then I should be redirected to the admin dashboard

  Scenario: Successful login as a customer
    When I enter my customer username "customer_user" and password "444"
    And I click the "LogInBtn" button
    Then I should be redirected to the customer dashboard

  Scenario: Successful login as an installer
    When I enter my installer username "installer_user" and password "installer_password"
    And I click the "LogInBtn" button
    Then I should be redirected to the installer dashboard

  Scenario: Failed login with incorrect credentials
    When I enter an invalid username "eman" and password "123"
    And I click the "LogInBtn" button
    Then I should see an error message "You have entered wrong value"

