Feature: Product Management

  Background:
    Given the user is on the Admin page

Scenario: Add a new product successfully
When the admin clicks on "Electronics" category
Then the admin should be in a "Electronics" product page
When the admin clicks on Management
Then the "Add Product" section should appear with delete button
When the admin clicks on Add Product
Then a form should appear
And the admin fills in the product details with new product : Product ID "657", Product Name "Radio", Information "Record your driving experiences...", Price "500", Section "Electronics", Number "60", Image "url"
And the manager submits the form
Then the system should display a success message: "Success: Product added successfully"

Scenario: Add a new product but it already existing product
When the admin clicks on "Electronics" category
Then the admin should be in a "Electronics" product page
When the admin clicks on Management
Then the "Add Product" section should appear with delete and update buttons
When the admin clicks on Add Product
Then a form should appear
And the admin fills in the product details: Product ID "157", Product Name "Light", Information "Record your driving experiences...", Price "500", Section "Electronics", Number "60", Image "url"
And the manager submits the form
Then the system should display an error message: "Error: Product Name already exist!"

  Scenario: Delete a product successfully
    When the admin clicks on "Electronics" category
    Then the admin should be in a "Electronics" product page
    When the admin clicks on Management
    Then the "Add Product" section should appear with delete and update buttons
    When the admin clicks on Delete "Radio" Product
    Then an alert should appear asking for confirmation Delete "Radio"
    When the admin clicks on Yes to delete "Radio"
    Then the system should display an success message: "Success: Product Deleted Successfully"

  Scenario: Update a product successfully
  When the admin clicks on "Exterior Accessories" category
  Then the admin should be in a "Exterior Accessories" product page
  When the admin clicks on Management
  Then the "Add Product" section should appear with delete and update buttons
  When the admin clicks on Update "Window Tint" Product
  Then a form should appear with the previous product data
    And the admin fills in the updated product details: Product Name "Window Tint", Information "Enhanced recording experiences...", Price "600", Section "Electronics", Number "70", Image "new_url"
   And the manager click on update to Sumbit the form and "Window Tint" be updated
   Then the system should display a success message: "Success: Product updated successfully"

  Scenario: Cancel delete
    When the admin clicks on "Exterior Accessories" category
    Then the admin should be in a "Exterior Accessories" product page
    When the admin clicks on Management
    Then admin clicks on Delete "Window Tint" Product
    Then an alert should appear asking for confirmation Delete "Window Tint"
    When the admin clicks on No to cancale delete "Window Tint"
    Then the product "Window Tint" should still be visible on the home page

  Scenario:Cancel Add a new product successfully
    When the admin clicks on "Exterior Accessories" category
    Then the admin should be in a "Exterior Accessories" product page
    When the admin clicks on Management
    Then the "Add Product" section should appear with delete button
    When the admin clicks on Add Product
    Then a form should appear
    And the admin clicks on Cancel
    Then The Add form should disappear

  Scenario: Cancel update
    When the admin clicks on "Exterior Accessories" category
    Then the admin should be in a "Exterior Accessories" product page
    When the admin clicks on Management
    When the admin clicks on Update "Window Tint" Product
    Then a form should appear with the previous product data
    And the admin clicks on Cancel Update
    Then the form should be closed