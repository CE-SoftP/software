Feature: Admin Dashboard - View and Manage Installation Accounts

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
    When the Admin navigates to the "View 6 Details" section
    Then the Admin should be redirected to the "Installation Details: 6" page
    When the admin change the time to "13:30"
    When the admin click on "SaveChanges"
    Then the changes should be edited successfully

  Scenario: Admin cancel editing a specific installation
    Given the Admin is logged in
    When the Admin navigates to the "viewInstallReq" section
    Then the Admin should be redirected to the "Installation table" page
    When the Admin navigates to the "View 6 Details" section
    Then the Admin should be redirected to the "Installation Details: 6" page
    When the admin change the time to "13:30"
    When the admin click on "Cancel"
    Then the changes should not be edited

  Scenario: Admin approave a specific installation
    Given the Admin is logged in
    When the Admin navigates to the "viewInstallReq" section
    Then the Admin should be redirected to the "Installation table" page
    When the Admin navigates to the "View 6 Details" section
    Then the Admin should be redirected to the "Installation Details: 6" page
    When the admin click on "Approve"
    Then the installation should be "approaved"

  Scenario: Admin delete a specific installation
    Given the Admin is logged in
    When the Admin navigates to the "viewInstallReq" section
    Then the Admin should be redirected to the "Installation table" page
    When the Admin navigates to the "View 6 Details" section
    Then the Admin should be redirected to the "Installation Details: 6" page
    When the admin click on "Delete"
    Then the installation should be "deleted"