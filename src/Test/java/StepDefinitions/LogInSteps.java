package StepDefinitions;



import java.util.List;
import java.util.logging.Logger;

import com.app.customer.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;


import java.net.http.HttpClient;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Duration;
public class LogInSteps {
    @InjectMocks
    private LogInSteps forgotPasswordSteps;


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


//    public LogInSteps(){
//        MockitoAnnotations.initMocks(this);
//    }


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
            driver.findElement(By.id(string)).click();
        sleep(6000);
    }

    @Then("I should be redirected to the admin dashboard")
    public void i_should_be_redirected_to_the_admin_dashboard() {
        Duration timeout = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, timeout);

        // Use a more descriptive ID for your element, if possible
        By viewElementLocator = By.id("view");
        WebElement someElementOnPage = wait.until(ExpectedConditions.presenceOfElementLocated(viewElementLocator));

        By viewElementLocator2 = By.id("management");
        WebElement someElementOnPage2 = wait.until(ExpectedConditions.presenceOfElementLocated(viewElementLocator));

        By viewElementLocator3 = By.id("viewInstall");
        WebElement someElementOnPage3 = wait.until(ExpectedConditions.presenceOfElementLocated(viewElementLocator));


        // Add assertions or further actions based on what you expect to see on the page
        // For example:
        Assertions.assertTrue(someElementOnPage.isDisplayed() & someElementOnPage2.isDisplayed() &someElementOnPage3.isDisplayed(), "The 'view' element is not displayed on the admin dashboard");

        // Add further assertions or actions if needed
        driver.close();
        driver.quit();
    }
//    private void handleAlert(WebDriverWait wait) {
//        Alert alert = driver.switchTo().alert();
//        String actualMessage = alert.getText();
//
//
//        alert.accept();
//
//            // Check if the alert has a button
//            boolean hasButton = alertHasButton();
//
//            if (hasButton) {
//                // Click the button to make the alert disappear using JavaScript
//                ((JavascriptExecutor) driver).executeScript("arguments[0].accept();", alert);
//            } else {
//                // Dismiss the alert using JavaScript
//                ((JavascriptExecutor) driver).executeScript("arguments[0].dismiss();", alert);
//            }
//
//    }
//
//    private boolean alertHasButton() {
//        try {
//            // Execute a script to check if the alert has a button
//            Object result = ((JavascriptExecutor) driver).executeScript(
//                    "return typeof(arguments[0].accept) !== 'undefined' || typeof(arguments[0].dismiss) !== 'undefined';",
//                    driver.switchTo().alert());
//
//            return Boolean.TRUE.equals(result);
//        } catch (WebDriverException e) {
//            // Alert has no button
//            return false;
//        }
//   }

    @When("I enter my customer username {string} and password {string}")
    public void i_enter_my_customer_username_and_password(String name, String pass) {
        driver.findElement(By.id("user_name")).sendKeys(name);
        driver.findElement(By.id("pass")).sendKeys(pass);
        sleep(2000);
    }


    @Then("I should be redirected to the customer dashboard")
    public void i_should_be_redirected_to_the_customer_dashboard() {
        Duration timeout = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        List<WebElement> elementsWithId = driver.findElements(By.cssSelector("[id]"));
        boolean found=false;
// Print the IDs of the elements
        for (WebElement element : elementsWithId) {
            if(element.getAttribute("id").equals("view") | element.getAttribute("id").equals("management") | element.getAttribute("id").equals("viewInstall")){
                found=true;
            }
        }

        By viewElementLocator = By.id("viewInstallReq");
        WebElement someElementOnPage = wait.until(ExpectedConditions.presenceOfElementLocated(viewElementLocator));

        By viewElementLocator2 = By.id("viewInstallHistory");
        WebElement someElementOnPage2 = wait.until(ExpectedConditions.presenceOfElementLocated(viewElementLocator));

        By viewElementLocator3 = By.id("viewOrderHistory");
        WebElement someElementOnPage3 = wait.until(ExpectedConditions.presenceOfElementLocated(viewElementLocator));

        Assertions.assertTrue(!found & someElementOnPage.isDisplayed() & someElementOnPage2.isDisplayed() &someElementOnPage3.isDisplayed(), "The 'view' element is not displayed on the admin dashboard");


//        boolean FinfView =true;
//        Duration timeout = Duration.ofSeconds(10);
//        WebDriverWait wait = new WebDriverWait(driver, timeout);
//        try {
//            By viewElementLocator = By.id("view");
//            WebElement someElementOnPage = wait.until(ExpectedConditions.presenceOfElementLocated(viewElementLocator));
//        }catch (Exception e){
//            System.out.println("Element not found: " + e.getMessage());
//            FinfView =false ;
//        }
//
//
//        // Add assertions or further actions based on what you expect to see on the page
//        // For example:
//        Assertions.assertTrue(!FinfView, "The 'view' element is not displayed on the admin dashboard");

    }



//        By viewElementLocator = By.id("view");
//        WebElement someElementOnPage = wait.until(ExpectedConditions.presenceOfElementLocated(viewElementLocator));

//        By viewElementLocator2 = By.id("viewInstallHistory");
//        WebElement someElementOnPage2 = wait.until(ExpectedConditions.presenceOfElementLocated(viewElementLocator2));
//        // Add assertions or further actions based on what you expect to see on the page
//        // For example:
//        Assertions.assertTrue(!someElementOnPage.isDisplayed() & someElementOnPage2.isDisplayed(), "The 'view' element is not displayed on the admin dashboard");
//
//        // Add further assertions or actions if needed


    @When("I enter my installer username {string} and password {string}")
    public void i_enter_my_installer_username_and_password(String name, String pass) {
        driver.findElement(By.id("user_name")).sendKeys(name);
        driver.findElement(By.id("pass")).sendKeys(pass);
        sleep(200);
    }

    @Then("I should be redirected to the installer dashboard")
    public void i_should_be_redirected_to_the_installer_dashboard() {
        Duration timeout = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        List<WebElement> elementsWithId = driver.findElements(By.cssSelector("[id]"));
        boolean found=false;
// Print the IDs of the elements
        for (WebElement element : elementsWithId) {
            if(element.getAttribute("id").equals("view") | element.getAttribute("id").equals("management") | element.getAttribute("id").equals("viewInstallHistory") | element.getAttribute("id").equals("viewOrderHistory") ){
                found=true;
            }
        }

        By viewElementLocator = By.id("viewInstall");
        WebElement someElementOnPage = wait.until(ExpectedConditions.presenceOfElementLocated(viewElementLocator));

        By viewElementLocator2 = By.id("viewInstallReq");
        WebElement someElementOnPage2 = wait.until(ExpectedConditions.presenceOfElementLocated(viewElementLocator));


        Assertions.assertTrue(!found & someElementOnPage.isDisplayed() & someElementOnPage2.isDisplayed() , "The 'view' element is not displayed on the admin dashboard");


        driver.close();
        driver.quit();
    }



    @When("I enter an invalid username {string} and password {string}")
    public void i_enter_an_invalid_username_and_password(String name, String pass) {
        driver.findElement(By.id("user_name")).sendKeys(name);
        driver.findElement(By.id("pass")).sendKeys(pass);
        sleep(200);
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String expectedMessage) {
        Alert alert = driver.switchTo().alert();
        String actualMessage = alert.getText();


        alert.accept();

        assertEquals(expectedMessage, actualMessage);

        driver.close();
        driver.quit();
    }


}
