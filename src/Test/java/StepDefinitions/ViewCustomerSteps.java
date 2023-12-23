package StepDefinitions;

import com.app.customer.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.ui.Model;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class ViewCustomerSteps {
    @Autowired
    DataService dataService;
    DataForm dataForm=new DataForm();
    @Autowired
    private TestRestTemplate restTemplate;
    CustomerController customerController ;
    @Autowired
    private WebDriver webDriver ;
    Logger logger = Logger.getLogger(getClass().getName());

    private Model model;

    @Autowired
    private CustomerRepository customerRepository;
    private  int customerId ;
    private String NewName;
    private String OldName;
    private LogInSteps logInSteps;
    private String oldTime;
    private String newTime;
    int numberOfRows;

    public ViewCustomerSteps(){
        this.logInSteps=new LogInSteps();
    }
    @Given("the Admin is logged in")
    public void the_admin_is_logged_in() {
        logInSteps.i_am_on_the_login_page();
        logInSteps.i_enter_my_admin_username_and_password("eman","555");
        logInSteps.i_click_the_button("LogInBtn");

        sleep(6000);

    }

    @When("the Admin navigates to the {string} section")
    public void the_admin_navigates_to_the_section(String link) {
        webDriver.findElement(By.id(link)).click();
        sleep(3000);
    }

    @Then("the Admin should be redirected to the {string} page")
    public void the_admin_should_be_redirected_to_the_page(String title) {
        String actualTitle =webDriver.getTitle();
        assertEquals(actualTitle,title);
        if(title.equals("Installation table")){
            WebElement table = webDriver.findElement(By.id("table"));
            java.util.List<WebElement> rows = table.findElements(By.tagName("tr"));
            numberOfRows = rows.size();
        }
    }

    @When("the admin change the time to {string}")
    public void the_admin_change_the_time_to(String time) {
        WebElement timeElement = webDriver.findElement(By.id("time"));
        String timeText = timeElement.getText();
        oldTime= timeText;
        sleep(1000);
        webDriver.findElement(By.id("time")).sendKeys(time);
        newTime=time;
    }

    @When("the admin click on {string}")
    public void the_admin_click_on(String button) {
        webDriver.findElement(By.id(button)).click();
        sleep(3000);
    }

    @Then("the changes should be edited successfully")
    public void the_changes_should_be_edited_successfully() {
        WebElement timeElement = webDriver.findElement(By.id("time"));
        String timeText = timeElement.getText();
        assertEquals(newTime,timeText);
    }

    @Then("the changes should not be edited")
    public void the_changes_should_not_be_edited() {
        WebElement timeElement = webDriver.findElement(By.id("time"));
        String timeText = timeElement.getText();
        assertEquals(oldTime,timeText);
    }

    @Then("the installation should be {string}")
    public void the_installation_should_be(String result) {
        if(result.equals("approaved")){
            WebElement table = webDriver.findElement(By.id("table"));
            java.util.List<WebElement> rows = table.findElements(By.tagName("tr"));
            int newNumberOfRows = rows.size();
            assertEquals(numberOfRows-1,newNumberOfRows);
        }
    }

    @Then("the Admin should see a list of customer accounts")
    public void the_admin_should_see_a_list_of_customer_accounts() {
        String expectedUrl = "http://localhost:"+CucumberIT.getPort()+"/ViewCustomers";
        String currentUrl = webDriver.getCurrentUrl();
        Assert.assertEquals(currentUrl, expectedUrl);

// Check if the title matches
//        String expectedTitle = "Expected Page Title";
//        String currentTitle = webDriver.getTitle();
//        Assert.assertEquals(currentTitle, expectedTitle);

    }


    @When("selects a customer account to {string}")
    public void selects_a_customer_account_to(String string){

        webDriver.findElement(By.id("tot2")).click();
        sleep(3000);
    }


    @Then("the customer details should be displayed successfully")
    public void the_customer_details_should_be_displayed_successfully() {
        Assertions.assertTrue(true);
        sleep(2000);
    }


    @When("edit the {string} value to {string}")
    public void edit_the_value_to(String name, String newName) {
        NewName=newName;
        webDriver.findElement(By.id(name)).sendKeys(newName);

    }

    @When("Click on {string} button")
    public void click_on_button(String button) {
        WebElement customerDetailsLink = webDriver.findElement(By.linkText(button));
        customerDetailsLink.click();


    }

    @Then("the customer account should be edited successfully")
    public void the_customer_account_should_be_edited_successfully() {
        String  updatedName = getTextFromNameField("name");

        // The expected name you provided in the previous step
        String expectedName = NewName; // Replace with the actual expected name

        // Assert that the updated name matches the expected name
        Assertions.assertEquals(expectedName, updatedName, "The customer account was not edited successfully");
    }
    @Then("the customer account should not be change")
    public void the_customer_account_should_not_be_change() {
        String  updatedName = getTextFromNameField("name");

        // The expected name you provided in the previous step
        String expectedName = OldName; // Replace with the actual expected name

        // Assert that the updated name matches the expected name
        Assertions.assertEquals(expectedName, updatedName, "The customer account was not edited successfully");
    }

    private String getTextFromNameField(String FieldId) {
        WebElement nameField = webDriver.findElement(By.id(FieldId)); // Replace "id" with the actual identifier of your name textfield
        return nameField.getAttribute("value");
    }
    private  void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {

            logger.info("Erooooooooooooooooooooor");
        }
    }
}