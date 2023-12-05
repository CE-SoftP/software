package StepDefinitions;



import java.util.logging.Logger;

import com.app.customer.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;


import java.net.http.HttpClient;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LogInSteps {
    @InjectMocks
    private LogInSteps forgotPasswordSteps;

    @Mock
    private MockEmailService mockEmailService;
    @Autowired
    CustomerController customerController;
    @Autowired
    DataService dataService;

   DataForm dataForm=new DataForm();

    private static HttpClient httpClient = HttpClient.newHttpClient();
    Logger logger = Logger.getLogger(getClass().getName());
    private WebDriver driver =new ChromeDriver();

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    private CustomerRepository cutomerRepository;

    private String userEmail;
    private ForgotPasswordController forgotPasswordController;

    public LogInSteps(){
        MockitoAnnotations.initMocks(this);
    }


    @Given("the app is running")
    public void the_app_is_running()
    {
       // driver =new ChromeDriver();

    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
      driver.get("http://localhost:"+CucumberIT.getPort()+"/");

    }
    @When("I enter my admin username {string} and password {string}")
    public void i_enter_my_admin_username_and_password(String name, String pass) {
        driver.findElement(By.id("user_name")).sendKeys(name);
        driver.findElement(By.id("pass")).sendKeys(pass);
        sleep(2000);
    }

    private  void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {

            logger.info("Erooooooooooooooooooooor");
        }
    }

    @When("I click the {string} button")
    public void i_click_the_button(String string) {
        if(string.equals("LogInBtn"))
            driver.findElement(By.id(string)).click();
        else{
//            String linkText = "ForgotPassword"; // Replace with the actual text of your link
//            String xpathExpression = "//a[contains(text(), '" + linkText + "')]";
//            WebElement forgotPasswordLink = driver.findElement(By.xpath(xpathExpression));
//            forgotPasswordLink.click();
            WebElement forgotPasswordLink = driver.findElement(By.id("ForgetPass"));

            // Simulate clicking the link
            forgotPasswordLink.click();
           // when(forgotPasswordController.handlePasswordResetRequest(userEmail)).thenReturn(true);
        }

        sleep(6000);
    }

    @Then("I should be redirected to the admin dashboard")
    public void i_should_be_redirected_to_the_admin_dashboard() {

    }

    @When("I enter my customer username {string} and password {string}")
    public void i_enter_my_customer_username_and_password(String name, String pass) {
        driver.findElement(By.id("username")).sendKeys(name);
        driver.findElement(By.id("password")).sendKeys(pass);
        sleep(2000);
    }


    @Then("I should be redirected to the customer dashboard")
    public void i_should_be_redirected_to_the_customer_dashboard() {
        dataForm.setUserName( driver.findElement(By.id("user_name")).getAttribute("value"));
        dataForm.setPassword( driver.findElement(By.id("pass")).getAttribute("value"));

        String result=dataService.searchAccount(dataForm);
        if(result.equals("Admin")){
            assert(true);
            ResponseEntity<String> response = restTemplate.getForEntity("/home", String.class);
            Assertions.assertEquals(200, response.getStatusCodeValue());
            String htmlContent = response.getBody();
            //  driver = new ChromeDriver();
            driver.get("data:text/html;charset=utf-8," + htmlContent);
            driver.get("");
            sleep(2000);}
        driver.close();
        driver.quit();
    }

    @When("I enter my installer username {string} and password {string}")
    public void i_enter_my_installer_username_and_password(String name, String pass) {
        driver.findElement(By.id("username")).sendKeys(name);
        driver.findElement(By.id("password")).sendKeys(pass);
        sleep(200);
    }

    @Then("I should be redirected to the installer dashboard")
    public void i_should_be_redirected_to_the_installer_dashboard() {
        dataForm.setUserName( driver.findElement(By.id("user_name")).getAttribute("value"));
        dataForm.setPassword( driver.findElement(By.id("pass")).getAttribute("value"));

        String result=dataService.searchAccount(dataForm);
        if(result.equals("Admin")){
            assert(true);
            ResponseEntity<String> response = restTemplate.getForEntity("/home", String.class);
            Assertions.assertEquals(200, response.getStatusCodeValue());
            String htmlContent = response.getBody();
            //  driver = new ChromeDriver();
            driver.get("data:text/html;charset=utf-8," + htmlContent);
            sleep(2000);}
        driver.close();
        driver.quit();
    }



    @When("I enter an invalid username {string} and password {string}")
    public void i_enter_an_invalid_username_and_password(String name, String pass) {
        driver.findElement(By.id("username")).sendKeys(name);
        driver.findElement(By.id("password")).sendKeys(pass);
        sleep(200);
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String string) {

       logger.info(" ");

        driver.close();
        driver.quit();
    }

    @When("I enter my email {string}")
    public void iEnterMyEmail(String email) {
        driver.findElement(By.id("username")).sendKeys(email);
        userEmail = email;
    }
    @Then("I should receive a password reset email")
    public void iShouldReceiveAPasswordResetEmail() {
        // Verify that the mock email service was called with the correct email address
        // You might also want to assert other conditions based on your implementation
        verify(mockEmailService).sendResetEmail(userEmail);
    }

}
