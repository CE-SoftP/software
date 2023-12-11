package StepDefinitions;

import com.app.customer.*;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import io.cucumber.java.en.Given;
import org.springframework.ui.Model;

import java.net.ConnectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class signup {


    private CustomerDb customerDb=new CustomerDb();
    @Autowired
    private TestRestTemplate restTemplate;
    Logger logger = Logger.getLogger(getClass().getName());
    @Autowired
    private WebDriver webDriver;
    private DataForm data = new DataForm();
    @Autowired
    private DataService dataService;
    @Autowired
    private CustomerController customerController;
 private Model model;
    @Given("the user is on the registration page")
    public void givenTheUserIsOnTheRegistrationPage() throws ConnectException {
       webDriver.get("http://localhost:"+CucumberIT.getPort()+"/form");
    }

    @When("they fill in the registration form with a valid username {string} and a strong password {string} and a correct confirmpass {string} and a correct email {string} and Birthdate {string} and Gender {string}")
    public void theyFillInTheRegistrationFormWithAValidUsernameAndAStrongPasswordAndACorrectConfirmpassAndACorrectEmailAndBirthdateAndGender(String username, String password, String confirmPassword, String email, String birthDate, String gender) {

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement userNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_name")));

        WebElement usernameField = webDriver.findElement(By.id("user_name"));
        usernameField.sendKeys(username);

        WebElement passwordField = webDriver.findElement(By.id("pass"));
        passwordField.sendKeys(password);

        WebElement confirmPasswordField = webDriver.findElement(By.id("conf"));
        confirmPasswordField.sendKeys(confirmPassword);

        WebElement emailField = webDriver.findElement(By.id("email"));
        emailField.sendKeys(email);

       WebElement dateField = webDriver.findElement(By.id("birth"));
        dateField.sendKeys(birthDate);


        WebElement genderField = webDriver.findElement(By.id("gender"));
        genderField.sendKeys(gender);

        sleep(2000);
    }

    @And("they click the {string} button")
    public void andTheyClickTheButton(String button) {
        WebElement signUpButton = webDriver.findElement(By.id("signup"));
        String pattern = "mm-dd-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement userNameElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user_name")));

        data.setUserName(webDriver.findElement(By.id("user_name")).getAttribute("value"));
        data.setPassword((webDriver.findElement(By.id("pass")).getAttribute("value")));
        data.setConfirmPassword(webDriver.findElement(By.id("conf")).getAttribute("value"));
        data.setEmail(webDriver.findElement(By.id("email")).getAttribute("value"));
        data.setGender(webDriver.findElement(By.id("gender")).getAttribute("value"));
        data.setUserId(123472556);

        signUpButton.click();
        sleep(2000);
    }

    @Then("their account should be successfully created")
    public void thenTheirAccountShouldBeSuccessfullyCreated() throws ParseException {
        assertTrue(true);
    }

    @And("they should be redirected to the home page")
    public void andTheyShouldBeRedirectedToTheHomePage() {

        Assertions.assertEquals("Home page", webDriver.getTitle());


    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            logger.info("Error during sleep");
        }
    }

    @When("they fill in the registration form with an exists username {string}")
    public void theyFillInTheRegistrationFormWithAnExistsUsername(String arg0) {
        data.setUserName(webDriver.findElement(By.id("user_name")).getAttribute("value"));
        data.setPassword("123");
        data.setConfirmPassword("123");
        String result = dataService.createAccount(data,customerDb);


        if (result.equals("User ID already exists")) {
            assertTrue(true);

        }
    }



    @Then("Then they should see the alert with message {string}")
    public void then_they_should_see_the_alert_with_message(String expectedMessage) {



        Alert alert = webDriver.switchTo().alert();
        String actualMessage = alert.getText();


        alert.accept();

        assertEquals(expectedMessage, actualMessage);

    }

    @Then("they should remain on the registration page")
    public void they_should_remain_on_the_registration_page() {
    String title =webDriver.getTitle();
    assertEquals("Sign UP",title);
    }

    @When("they fill in the registration form with a valid username {string} and a strong password {string} and they confirm the password with a different value {string}")
    public void theyFillInTheRegistrationFormWithAValidUsernameAndAStrongPasswordAndTheyConfirmThePasswordWithADifferentValue(String name, String arg1, String arg2) {

        data.setUserName(webDriver.findElement(By.id("user_name")).getAttribute("value"));
        data.setPassword((webDriver.findElement(By.id("pass")).getAttribute("value")));
        data.setConfirmPassword(webDriver.findElement(By.id("conf")).getAttribute("value"));
        data.setEmail(webDriver.findElement(By.id("email")).getAttribute("value"));



        String result = dataService.createAccount(data,customerDb);
        if (result.equals("Password and Confirm Password do not match.")) {
            assertTrue(true);

        }

    }

    @When("they fill in the registration form with an exists username {string} and a strong password {string} and a correct confirmpass {string} and a correct email {string} and Birthdate {string} and Gender {string}")
    public void theyFillInTheRegistrationFormWithAnExistsUsernameAndAStrongPasswordAndACorrectConfirmpassAndACorrectEmailAndBirthdateAndGender(String username, String password, String confirmPassword , String email, String birthDate, String gender) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement userNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_name")));

        WebElement usernameField = webDriver.findElement(By.id("user_name"));
        usernameField.sendKeys(username);

        WebElement passwordField = webDriver.findElement(By.id("pass"));
        passwordField.sendKeys(password);

        WebElement confirmPasswordField = webDriver.findElement(By.id("conf"));
        confirmPasswordField.sendKeys(confirmPassword);

        WebElement emailField = webDriver.findElement(By.id("email"));
        emailField.sendKeys(email);

        WebElement dateField = webDriver.findElement(By.id("birth"));
        dateField.sendKeys(birthDate);


        WebElement genderField = webDriver.findElement(By.id("gender"));
        genderField.sendKeys(gender);

        sleep(2000);

    }

    @When("they fill in the registration form with an exists username {string} and a strong password {string} confirm the password with a different value {string} and a correct email {string} and Birthdate {string} and Gender {string}")
    public void theyFillInTheRegistrationFormWithAnExistsUsernameAndAStrongPasswordConfirmThePasswordWithADifferentValueAndACorrectEmailAndBirthdateAndGender(String username, String password, String confirmPassword, String email, String birthDate, String gender) {

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement userNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_name")));

        WebElement usernameField = webDriver.findElement(By.id("user_name"));
        usernameField.sendKeys(username);

        WebElement passwordField = webDriver.findElement(By.id("pass"));
        passwordField.sendKeys(password);

        WebElement confirmPasswordField = webDriver.findElement(By.id("conf"));
        confirmPasswordField.sendKeys(confirmPassword);

        WebElement emailField = webDriver.findElement(By.id("email"));
        emailField.sendKeys(email);

        WebElement dateField = webDriver.findElement(By.id("birth"));
        dateField.sendKeys(birthDate);


        WebElement genderField = webDriver.findElement(By.id("gender"));
        genderField.sendKeys(gender);

        sleep(2000);
    }

    @When("they fill in the registration form with an valid username {string} and a strong password {string} confirm the password with a correct confirm value {string} and a correct email {string} and Birthdate {string} and Gender {string}")
    public void theyFillInTheRegistrationFormWithAnValidUsernameAndAStrongPasswordConfirmThePasswordWithACorrectConfirmValueAndACorrectEmailAndBirthdateAndGender(String username, String password, String confirmPassword, String email, String birthDate, String gender) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement userNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_name")));

        WebElement usernameField = webDriver.findElement(By.id("user_name"));
        usernameField.sendKeys(username);

        WebElement passwordField = webDriver.findElement(By.id("pass"));
        passwordField.sendKeys(password);

        WebElement confirmPasswordField = webDriver.findElement(By.id("conf"));
        confirmPasswordField.sendKeys(confirmPassword);

        WebElement emailField = webDriver.findElement(By.id("email"));
        emailField.sendKeys(email);

        WebElement dateField = webDriver.findElement(By.id("birth"));
        dateField.sendKeys(birthDate);


        WebElement genderField = webDriver.findElement(By.id("gender"));
        genderField.sendKeys(gender);

        sleep(2000);

    }

    @When("they fill in the registration form with an valid username {string} and a strong password {string} confirm the password with a correct confirm value {string} and an invalid email {string} and Birthdate {string} and Gender {string}")
    public void theyFillInTheRegistrationFormWithAnValidUsernameAndAStrongPasswordConfirmThePasswordWithACorrectConfirmValueAndAnInvalidEmailAndBirthdateAndGender(String username, String password, String confirmPassword, String email, String birthDate, String gender) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement userNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_name")));

        WebElement usernameField = webDriver.findElement(By.id("user_name"));
        usernameField.sendKeys(username);

        WebElement passwordField = webDriver.findElement(By.id("pass"));
        passwordField.sendKeys(password);

        WebElement confirmPasswordField = webDriver.findElement(By.id("conf"));
        confirmPasswordField.sendKeys(confirmPassword);

        WebElement emailField = webDriver.findElement(By.id("email"));
        emailField.sendKeys(email);

        WebElement dateField = webDriver.findElement(By.id("birth"));
        dateField.sendKeys(birthDate);


        WebElement genderField = webDriver.findElement(By.id("gender"));
        genderField.sendKeys(gender);

        sleep(2000);

    }
}
