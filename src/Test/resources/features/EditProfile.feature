Feature: User Profile


  Scenario: Edit Profile Information
    Given I am a logged-in customer
    When I navigate to the "profile" section
    Then I should be redirected to the "profile" page
    When I click on the "editButton" button
    And I edit my name to "noor"
    And I click on the "save" button
    Then my name should be changed successfully

  Scenario: Cancel Editing Profile Information
    Given I am a logged-in customer
    When I navigate to the "profile" section
    And I click on the "edit" button
    And I edit my name to "noor"
    And I click on the "cancel" button
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
    When I navigate to the "viewInstallReq" section
    Then I should be redirected to the "Installation Table" page

  Scenario: View Specific Installation
    Given I am a logged-in customer
    When I navigate to the "viewInstallReq" section
    Then I should be redirected to the "Installation Table" page
    When I navigate to the "View 6 Details" section
    Then I should be redirected to the "Installation 6 Details" page


