Feature: User Movement

Background:
Given the user is on the home page


  Scenario: search Product successfully
    And  the user see all categories
    When the user click on "searchMenu" button
    And the user want to search for "Car paint"
    Then the user click on search button
    Then the user should see the searched product "Car paint"


  Scenario: Searching for Nonexistent Product
    And  the user see all categories
    When the user click on "searchMenu" button
    And  the user want to search for "blower"
    Then the user click on search button
    Then  the user should receive an message indicating that "The product not found"

    When the user click on "back" he should back to previous page


  Scenario: User views interior car accessory categories And see it's information
    When the user clicks on the "Interior Accessories" div
    Then the user navigates to the "Interior Accessories" section
    When the user clicks on the "Seat covers" to see it's Information
    Then the user should see information about "Seat covers"



  Scenario: View information for Floor Mate And decide to Add it to the cart

    When the user clicks on the "Interior Accessories" div
    Then the user navigates to the "Interior Accessories" section
    When the user clicks on the "Floor mats" to see it's Information
    Then the user should see information about "Floor mats"
    When the user click on "add_toCart" button
    Then the user should see a message indicate that the "product is added successfully"
    When the user click on "back" he should back to previous page





  Scenario: Adding unAvailable Product
    When the user clicks on the "Interior Accessories" div
    Then the user navigates to the "Interior Accessories" section
    When the user clicks on the "Dashboard covers" to see it's Information
    Then the user should see information about "Floor mats"
    When the user click on "add_toCart" button
    Then  the user should receive an message indicating that "the product not available for now"
