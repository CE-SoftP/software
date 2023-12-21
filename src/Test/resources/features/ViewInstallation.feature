Feature: Admin Dashboard - View and Manage Installation Accounts

  Scenario: Admin views installations
    Given the Admin is logged in
    When the Admin navigates to the "viewInstall" section
    Then the Admin should be redirected to the "Installation Table" page

  Scenario: Admin views specific installation
    Given the Admin is logged in
    When the Admin navigates to the "viewInstall" section
    And selects the "View 1 Details" section
    Then the Admin should be redirected to the "Installation 1 Details" page

  Scenario: Admin edits a specific installation
    Given the Admin is logged in
    When the Admin navigates to the "viewInstall" section
    And selects the "View 1 Details" section
    Then the Admin should be redirected to the "Installation 1 Details" page
    When the admin click on "approve"
    Then the installation should be "approaved"

  Scenario: Admin edits a specific installation
    Given the Admin is logged in
    When the Admin navigates to the "viewInstall" section
    And selects the "View 1 Details" section
    Then the Admin should be redirected to the "Installation 1 Details" page
    When the admin click on "delete"
    Then the installation should be "deleted"