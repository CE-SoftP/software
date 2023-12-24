Feature: Notification

  Scenario: customer had order confirmation to see
    When I logged in as a customer with name "customer_user" and password "444"
    And I have order confirmation
    Then a pop up for "order" will be shown

  Scenario: customer had installation updates to see
    When I logged in as a customer with name "toqa" and password "555"
    And I have installation updates
    Then a pop up for "installation" will be shown

  Scenario: customer had installation updates and order confirmation to see
    When I logged in as a customer with name "toqa" and password "555"
    And I have installation updates
    And I have order confirmation
    Then a pop up for "installation and order" will be shown

  Scenario: customer had no notification to see
    When I logged in as a customer with name "customer_user" and password "444"
    And I have no notification to see
    Then i should be redirected to the home page

  Scenario: installer had installation requests to see
    When I logged in as an installer with name "installer_user" and password "installer_password"
    And I have installation requests
    Then a pop up for "installation" will be shown or i will be redirected to the home page if there is no requests

  Scenario: admin had installation requests to see
    When I logged in as an admin with name "eman" and password "555"
    And I have installation requests
    Then a pop up for "installation" will be shown or i will be redirected to the home page if there is no requests