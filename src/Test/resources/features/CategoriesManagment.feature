Feature: Categories Management


Background:
Given the user is on the Admin page

Scenario: Add a new category successfully
When the admin clicks on Management
Then the "Add Category" section should appear with delete button
When the admin clicks on Add Category
Then a form should appear
And the admin fills in the category details: Category Name "Luxury Parts"
And the manager submits the Add Categories form
Then the system should display a success save categories message: "Category added successfully"


Scenario: Add a new category with an existing category
When the admin clicks on Management
Then the "Add Category" section should appear with delete button
When the admin clicks on Add Category
Then a form should appear
And the admin fills in the category details: Category Name "Exterior Accessories"
And  the manager submits the Add Categories form
Then the system should display an error message: "Category already exists"


Scenario: Delete a category successfully
When the admin clicks on Management
Then the "Add Category" section should appear with delete button
When the admin clicks on Delete Category
Then an alert should appear asking for confirmation
When the admin clicks on Yes
Then the category should be deleted successfully
And the category should not be visible on the home page

Scenario: Cancel deleting a category
When the admin clicks on Management
Then the "Add Category" section should appear with delete button
When the admin clicks on Delete Category
Then an alert should appear asking for confirmation
When the admin clicks on No
Then the category should still be visible on the home page


