Feature: Signup



  Scenario:  Successful Sign Up with Minimal Information
    Given  the user is on the registration page
    And they fill in the registration form with a valid username "Ahmad" and a strong password "customersecure1234" and a correct confirmpass "customersecure1234" and a correct email "ahmadomar24@gmail.com" and Birthdate "1999-2-1" and Gender "Male"
    And they click the "Sign Up" button
    Then their account should be successfully created
    And they should be redirected to the home page


  Scenario: username already exist
    Given the user is on the registration page
    When they fill in the registration form with an exists username "Hani" and a strong password "s123ss" and a correct confirmpass "s123ss" and a correct email "AliIshtawi@gmail.com" and Birthdate "1998-5-1" and Gender "Male"
    And they click the "Sign Up" button
    Then  Then they should see the alert with message "Error: User Name already exists"
    And they should remain on the registration page

  Scenario: Password Confirmation Mismatch during Registration
    Given the user is on the registration page
    When they fill in the registration form with an exists username "Ali" and a strong password "s123ss" confirm the password with a different value "MismatchedPassword456" and a correct email "user123@example.com" and Birthdate "1998-7-2" and Gender "Male"
    And they click the "Sign Up" button
    Then  Then they should see the alert with message "Error: Password and Confirm Password do not match"
    And   they should remain on the registration page

  Scenario: Invalid Email
    Given the user is on the registration page
    When they fill in the registration form with an valid username "EMAN_20" and a strong password "S12354SD" confirm the password with a correct confirm value "S12354SD" and an invalid email "eMANgmail.com" and Birthdate "1998-7-2" and Gender "Female"
    And they click the "Sign Up" button
    Then  Then they should see the alert with message "Error: Email already exists Enter another one"
    And   they should remain on the registration page


  Scenario:Email Already Exist
    Given the user is on the registration page
    When they fill in the registration form with an valid username "Toqa_200" and a strong password "S12354SD" confirm the password with a correct confirm value "S12354SD" and a correct email "toqaomar24@gmail.com" and Birthdate "1998-7-2" and Gender "Female"
    And they click the "Sign Up" button
    Then  Then they should see the alert with message "Error: Please enter a valid email address"
    And   they should remain on the registration page
