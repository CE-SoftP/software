Feature: Categories Management


Background:
Given the user is on the Admin page
  When the admin clicks on Management
  Then the "Add Category" section should appear with delete button

Scenario: Add a new category successfully
When the admin clicks on Add Category
Then a form should appear
And the admin fills in the category details: Category Name "Luxury Parts"
Then the manager submits the Add Categories form
And the Catagory "Luxury Parts" should add successfully

  Scenario: Cancel Add a new category successfully
    When the admin clicks on Add Category
    Then a form should appear
    And  the manager click on "Cancel" button
    Then the form should disappear


Scenario: Add a new category with an existing category
When the admin clicks on Add Category
Then a form should appear
And the admin fills in the category details: Category Name "Exterior Accessories"
And  the manager submits the Add Categories form
Then the system should display an error message: "Error: Category Name already exists"


Scenario: Delete a category successfully

When the admin clicks on Delete "Luxury Parts" Category
Then an alert should appear asking for confirmation Delete "Luxury Parts"
When the admin clicks on Yes to delete "Luxury Parts"
Then the system should display an success message: "Success: Category Deleted Successfully"


Scenario: Cancel deleting a category
When the admin clicks on Delete "Exterior Accessories" Category
  Then an alert should appear asking for confirmation Delete "Exterior Accessories"
When the admin clicks on No to cancale delete "Exterior Accessories"
Then the category "Exterior Accessories" should still be visible on the home page


