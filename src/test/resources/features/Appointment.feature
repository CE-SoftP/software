Feature: Appointment or Buy online

  Scenario: User Takes an Appointment Successfully
    Given the user is on the chosen page
    When the user clicks on the "Add Appointment" button
    Then a dialog box should appear
    And the user selects "Sunday" and "12-1" and "Paint the Car"
    And the user submits the appointment request
    Then the request should be saved successfully


  Scenario: User decides to Buy online
    Given the user is on the chosen page
    When the user clicks on the "Buy online" button1
    Then the user should be on the home page



  Scenario: User Buy Successfully
    Given the user is on the home page
    When the user descide to buy a "Car paint" from "Exterior Accessories"
    Then the user click on "Car paint" to see its information
    And the user decides to buy it and click "Add to Cart"
    Then the selected items remain in the cart




  Scenario: User Applies a Discount Code During Checkout
    Given the user has items in the cart
    When the user navigates to the checkout page
    Then the user enters a valid discount code for a promotional offer
    And the user applies the discount to the total amount
    And the user successfully completes the purchase with the discounted price

  Scenario: User Adds and Manages Items in the Shopping Cart
    Given the user is on the home page
    When the user adds "Car polish" and "Microfiber Cloth" to the cart
    Then the user views the cart to verify the selected items
    And the user removes "Microfiber Cloth" from the cart
    And the user proceeds to checkout with only "Car polish"

  Scenario: User Explores Customer Reviews Before Buying
    Given the user is on the product information page for "Car paint"
    When the user scrolls down to the customer reviews section
    Then the user reads several reviews to make an informed decision
    And the user adds "Car paint" to the cart based on positive reviews
    And the user proceeds to checkout with the selected item

  Scenario: User Abandons Cart and Receives a Reminder
    Given the user has items in the cart
    When the user decides not to proceed to checkout and leaves the page
    Then after a certain time, the user receives an email reminder about the abandoned cart
    And the email encourages the user to complete the purchase with a direct link to the cart