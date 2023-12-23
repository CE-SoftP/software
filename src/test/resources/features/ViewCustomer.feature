Feature: Admin Dashboard - View and Manage Customer Accounts

  Scenario: Admin views customer accounts
    Given the Admin is logged in
    When the Admin navigates to the "View Customers" section
    Then the Admin should see a list of customer accounts

  Scenario: Admin chose a customer account to show details
    Given the Admin is logged in
    When the Admin navigates to the "View Customers" section
    And selects a customer account to "Toqa"
    Then the customer details should be displayed successfully

  Scenario: Admin edit a customer account
    Given the Admin is logged in
    When the Admin navigates to the "View Customers" section
    And selects a customer account to "Hani"
    And edit the Name "Hani" value to "Hani_new"
    And Click on "SaveChanges" button
    Then the customer account should be edited successfully

  Scenario: Admin deactivates a customer account
    Given the Admin is logged in
    When the Admin navigates to the "View Customers" section
    And selects a customer account to "toqa22"
    And edit the Name "name" value to "mone_new"
    And Click on "Cancel" button
    Then the customer account should not be change

  Scenario: Admin deactivates a customer account
    Given the Admin is logged in
    When the Admin navigates to the "View Customers" section
    And selects a customer account to "deactivate"
    And confirms the "deactivation"
    Then the customer account should be deactivated successfully

  Scenario: Admin deactivates a customer account
    Given the Admin is logged in
    When the Admin navigates to the "View Customers" section
    And selects a customer account to "deactivate"
    And cancel the "deactivation"
    Then the customer account should be deactivated successfully