package StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class profileSteps {
    Logger logger = Logger.getLogger(getClass().getName());
    @Autowired
    WebDriver driver;
    private LogInSteps logInSteps;
    String newName="new";
    String oldName;
    public profileSteps(){
        this.logInSteps = new LogInSteps();
    }


    @Given("I am a logged-in customer")
    public void i_am_a_logged_in_customer() {
        driver.get("http://localhost:"+CucumberIT.getPort()+"/");

        driver.findElement(By.id("user_name")).sendKeys("new");
        driver.findElement(By.id("pass")).sendKeys("789");
        sleep(2000);

        driver.findElement(By.id("LogInBtn")).click();

        oldName="customer_user";
        sleep(3000);
    }

    @When("I navigate to the {string} section")
    public void i_navigate_to_the_section(String link) {
        driver.findElement(By.id(link)).click();
        sleep(3000);
    }

    @Then("I should be redirected to the {string} page")
    public void i_should_be_redirected_to_the_page(String string) {
        String title =driver.getTitle();
        assertEquals(string,title);
    }


    @When("I click on the {string} button")
    public void i_click_on_the_button(String button) {
        driver.findElement(By.id(button)).click();
        sleep(3000);
    }

    @When("I edit my name to {string}")
    public void i_edit_my_name_to(String name) {
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys(name);
        newName=name;
        sleep(3000);
    }

    @Then("my name should be changed successfully")
    public void my_name_should_be_changed_successfully() {
        WebElement userEmailElement = driver.findElement(By.id("userName"));
        String userEmailText = userEmailElement.getText();
        assertEquals(userEmailText,newName);

    }

    @Then("my name should not be changed")
    public void my_name_should_not_be_changed() {
        WebElement userEmailElement = driver.findElement(By.id("userName"));
        String userEmailText = userEmailElement.getText();
        assertEquals(userEmailText,oldName);
    }

    private  void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {

            logger.info("Erooooooooooooooooooooor");
        }
    }


}
