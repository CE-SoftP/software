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
    And edit the "Hani" value to "Hani_new"
    And Click on "SaveChanges" button
    Then the customer account should be edited successfully

  Scenario: Admin deactivates a customer account
    Given the Admin is logged in
    When the Admin navigates to the "View Customers" section
    And selects a customer account to "toqa22"
    And edit the "toqa22" value to "mone_new"
    And Click on "Cancel" button
    Then the customer account should not be change

  Scenario: Admin views order confirmations
    Given the Admin is logged in
    When the Admin navigates to the "viewOrderconfirmation" section
    Then the Admin should be redirected to the "Order Table" page

  Scenario: Admin views specific order
    Given the Admin is logged in
    When the Admin navigates to the "viewOrderconfirmation" section
    Then the Admin should be redirected to the "Order Table" page
    When the Admin navigates to the "View 1 Details" section
    And  Click on "Confirm" button


  Scenario: Admin views installations
    Given the Admin is logged in
    When the Admin navigates to the "viewInstall" section
    Then the Admin should be redirected to the "Installation Table" page

  Scenario: Admin views specific installation
    Given the Admin is logged in
    When the Admin navigates to the "viewInstall" section
    Then the Admin should be redirected to the "Installation Table" page
    When the Admin navigates to the "View 1 Details" section
    Then the Admin should be redirected to the "Installation Details: 1" page


  Scenario: Admin edits a specific installation
    Given the Admin is logged in
    When the Admin navigates to the "viewInstallReq" section
    Then the Admin should be redirected to the "Installation table" page
    When the Admin navigates to the "View 1 Details" section
    Then the Admin should be redirected to the "Installation Details: 1" page
    When the admin change the time to "13:30"
    When the admin click on "SaveChanges"
    Then the changes should be edited successfully

  Scenario: Admin cancel editing a specific installation
    Given the Admin is logged in
    When the Admin navigates to the "viewInstallReq" section
    Then the Admin should be redirected to the "Installation table" page
    When the Admin navigates to the "View 3 Details" section
    Then the Admin should be redirected to the "Installation Details: 3" page
    When the admin change the time to "13:30"
    When the admin click on "Cancel"
    Then the changes should not be edited

  Scenario: Admin approave a specific installation
    Given the Admin is logged in
    When the Admin navigates to the "viewInstallReq" section
    Then the Admin should be redirected to the "Installation table" page
    When the Admin navigates to the "View 3 Details" section
    Then the Admin should be redirected to the "Installation Details: 3" page
    When the admin click on "Approve"
    Then the Admin should be redirected to the "Installation table" page
    And the installation should be "approaved"

  Scenario: Admin delete a specific installation
    Given the Admin is logged in
    When the Admin navigates to the "viewInstallReq" section
    Then the Admin should be redirected to the "Installation table" page
    When the Admin navigates to the "View 3 Details" section
    Then the Admin should be redirected to the "Installation Details: 3" page
    When the admin click on "Delete"
    Then the installation should be "deleted"

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