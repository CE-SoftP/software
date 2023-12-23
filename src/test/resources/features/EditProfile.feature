Feature: User Profile

  Scenario: Cancel Editing Profile Information
    Given I am a logged-in customer
    When I navigate to the "profile" section
    And I click on the "editButton" button
    And I edit my name to "new"
    And I click on the "cancelButton" button
    Then my name should not be changed

  Scenario: View Order History
    Given I am a logged-in customer
    When I navigate to the "viewOrderHistory" section
    Then I should be redirected to the "Order Table" page

  Scenario: View Specific Order
    Given I am a logged-in customer
    When I navigate to the "viewOrderHistory" section
    Then I should be redirected to the "Order Table" page
    When I navigate to the "View 1 Details" section
    Then I should be redirected to the "Order 1 Details" page

  Scenario: View Installation History
    Given I am a logged-in customer
    When I navigate to the "viewInstallHistory" section
    Then I should be redirected to the "Customer Table" page

  Scenario: Edit Profile Information
    Given I am a logged-in customer
    When I navigate to the "profile" section
    Then I should be redirected to the "User Profile" page
    When I click on the "editButton" button
    And I edit my name to "noor"
    And I click on the "saveButton" button
    Then my name should be changed successfully

  Scenario: View Specific Installation
    Given I am a logged-in customer
    When I navigate to the "viewInstallHistory" section
    Then I should be redirected to the "Customer Table" page
    When I navigate to the "View 1 Details" section
    Then I should be redirected to the "Installation 1 Details" page


