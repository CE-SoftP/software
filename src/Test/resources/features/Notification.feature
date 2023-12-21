Feature: Notification

  Scenario: customer had order confirmation to see
    When I logged in as a customer
    And I have order confirmation
    Then a pop up for "order" will be shown

  Scenario: customer had no order confirmation to see
    When I logged in as a customer
    And I have no order confirmations
    Then i should be redirected to the home page

  Scenario: customer had installation updates to see
    When I logged in as a customer
    And I have installation updates
    Then a pop up for "installation" will be shown

  Scenario: installer had installation requests to see
    When I logged in as an installer
    And I have installation requests
    Then a pop up for "installation" will be shown

  Scenario: installer had no installation requests to see
    When I logged in as an installer
    And I have no installation requests
    Then i should be redirected to the home page

  Scenario: admin had installation requests to see
    When I logged in as an admin
    And I have installation requests
    Then a pop up for "installation" will be shown

  Scenario: admin had no installation requests to see
    When I logged in as an admin
    And I have no installation requests
    Then i should be redirected to the home page