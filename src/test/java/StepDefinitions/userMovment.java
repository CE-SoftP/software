package StepDefinitions;

import com.app.maneger_and_product.ManegerController;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;


public class userMovment {

    @Autowired
    WebDriver webDriver;
    @Autowired
    private ManegerController manegerController;
    @Given("the user is on the home page")
    public void the_user_is_on_the_home_page() {
        webDriver.get("http://localhost:"+CucumberIT.getPort()+"/");

        webDriver.findElement(By.id("user_name")).sendKeys("toqa22");
        webDriver.findElement(By.id("pass")).sendKeys("666");
        sleep(2);



    }
    @Given("the user see all categories")
    public void the_user_see_all_categories() {
       boolean result =webDriver.findElement(By.id("Electronics")).isDisplayed();
        Assert.assertTrue(result);
    }

    @When("the user click on {string} button")
    public void the_user_click_on_button(String search) {
      webDriver.findElement(By.id(search)).click();
      sleep(2);
    }

    @When("the user want to search for {string}")
    public void the_user_want_to_search_for(String searchElement) {
        webDriver.findElement(By.id("searchInput")).sendKeys(searchElement);
    }

    @Then("the user click on search button")
    public void the_user_click_on_search_button() {
      webDriver.findElement(By.id("searchProduct")).click();
    }

    @Then("the user should see the searched product {string}")
    public void theUserShouldSeeTheSearchedProduct(String searchElement) {
     String result=webDriver.getTitle();
     Assert.assertEquals("Product Details" , result);
    }


    @When("the user clicks on the {string} div")
    public void the_user_clicks_on_the_div(String interiorDiv) {
      webDriver.findElement(By.id(interiorDiv)).click();
      sleep(2);
    }

    @Then("the user navigates to the {string} section")
    public void the_user_navigates_to_the_section(String interiorDiv) {
       String result=webDriver.getTitle();
       Assert.assertEquals("Product List" , result);
       sleep(2);
    }

    @When("the user clicks on the {string} to see it's Information")
    public void the_user_clicks_on_the_to_see_it_s_information(String productDiv) {
       webDriver.findElement(By.id(productDiv)).click();
       sleep(2);
    }

    @Then("the user should see information about {string}")
    public void theUserShouldSeeInformationAbout(String productDiv) {
        String result=webDriver.getTitle();
        Assert.assertEquals("Product Details" , result);
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception e) {

        }
    }
    @Then("the user should see a message indicate that the {string}")
    public void theUserShouldSeeAMessageIndicateThatThe(String expectedErrorMessage) {
        String actualErrorMessage = webDriver.findElement(By.id("errorMessage")).getText();
        Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
       sleep(2);
    }


    @When("the user click on {string} he should back to previous page")
    public void theUserClickOnHeShouldBackToPreviousPage(String back) {
        webDriver.findElement(By.id(back)).click();
        sleep(2);
    }

    @Then("the user should receive an message indicating that {string}")
    public void theUserShouldReceiveAnMessageIndicatingThat(String expectedErrorMessage) {
        String actualErrorMessage = webDriver.findElement(By.id("errorMessage")).getText();
        Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
    }
}
