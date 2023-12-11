Feature: Signup



  Scenario:  Successful Sign Up with Minimal Information
    Given  the user is on the registration page
    And they fill in the registration form with a valid username "Ahmad" and a strong password "customersecure1234" and a correct confirmpass "customersecure1234" and a correct email "ahmadomar24@gmail.com" and Birthdate "1999-2-1" and Gender "Male"
    And they click the "Sign Up" button
    Then their account should be successfully created
    And they should be redirected to the home page


  Scenario: username already exist
    Given the user is on the registration page
    When they fill in the registration form with an exists username "Ali" and a strong password "s123ss" and a correct confirmpass "s123ss" and a correct email "AliIshtawi@gmail.com" and Birthdate "1998-5-1" and Gender "Male"
    And they click the "Sign Up" button
    Then they should see the alert with message "User Name already exists"
    And click on "ok" button
    And they should remain on the registration page

  Scenario: Password Confirmation Mismatch during Registration
    Given the user is on the registration page
    When they fill in the registration form with an existing username "Hani" and a strong password "s123ss," confirm the password with a different value "MismatchedPassword456," and a correct email "HaniAli@gmail.com," Birthdate "1998-7-2," and Gender "Male"
    Then they should see the alert with the message "Password and Confirm Password do not match"
    And they should remain on the registration page

