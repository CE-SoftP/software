Feature: User Login

  Background:
    Given the app is running
    And I am on the login page

  Scenario: Successful login as an admin
    When I enter my admin username "eman" and password "555"
    And I click the "LogInBtn" button
    Then I should be redirected to the admin dashboard

  Scenario: Successful login as a customer
    When I enter my customer username "customer_user" and password "customer_password"
    And I click the "LogInBtn" button
    Then I should be redirected to the customer dashboard

  Scenario: Successful login as an installer
    When I enter my installer username "installer_user" and password "installer_password"
    And I click the "LogInBtn" button
    Then I should be redirected to the installer dashboard

  Scenario: Failed login with incorrect credentials
    When I enter an invalid username "eman" and password "123"
    And I click the "LogInBtn" button
    Then I should see an error message "Invalid credentials"

  Scenario: Forgot Password
    When I enter my email "myemail@example.com"
    And I click the "Forgot Password?" button
    Then I should receive a password reset email
#
#  Scenario: Password Reset
#    Given I have received a password reset email
#    When I click the reset password link in the email
#    And I set a new password
#    And I click the "Save" button
#    Then I should be able to log in with the new password
#  │ src/test/resources/cucumber.properties:          cucumber.publish.enabled=true    │
#  │ src/test/resources/junit-platform.properties:    cucumber.publish.enabled=true    │
#  │ Environment variable:                            CUCUMBER_PUBLISH_ENABLED=true    │
#  │ JUnit:                                           @CucumberOptions(publish = true)

