package StepDefinitions;


import com.app.appointment.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

public class UserDecide {

    Logger logger = Logger.getLogger(getClass().getName());
    @Autowired
    AppointmenRepository appointmenRepository;
    @Autowired
    AppointmentService appointmentService=new AppointmentService(appointmenRepository );
 WebDriver webDriver=null;
    @Given("the user is on the chosen page")
    public void the_user_is_on_the_chosen_page() {
       webDriver=new ChromeDriver();
       webDriver.get("file://C://Users//PC//Desktop//SoftPro//src//main//resources//templates//chose.html");
       sleep(500);
    }

    @When("the user clicks on the {string} button")
    public void the_user_clicks_on_the_button(String string) {
        webDriver.findElement(By.id("Appointment")).click();
        sleep(500);
    }

    @Then("a dialog box should appear")
    public void a_dialog_box_should_appear() {


        By dialogBoxLocator = By.id("appointmentForm");
        WebElement dialogBox = webDriver.findElement(dialogBoxLocator);
        assertTrue("Dialog box should be visible", dialogBox.isDisplayed());
        sleep(2000);
    }

    @And("the user selects {string} and {string} and {string}")
    public void theUserSelectsAndAnd(String day, String hour, String service) {

        WebElement daySelect = webDriver.findElement(By.id("Day"));
        Select select = new Select(daySelect);
        select.selectByVisibleText(day);

        WebElement hourSelect = webDriver.findElement(By.id("hours"));
        Select select1 = new Select(hourSelect);
        select1.selectByVisibleText(hour);

        WebElement serviceSelect = webDriver.findElement(By.id("Service"));
        Select select2 = new Select(serviceSelect);
        select2.selectByVisibleText(service);


    }
    @Then("the user submits the appointment request")
    public void the_user_submits_the_appointment_request() {
        AppointmentForm appointmentForm =new AppointmentForm();
        appointmentForm.setDay(webDriver.findElement(By.id("Day")).getAttribute("value"));
        appointmentForm.setHour( webDriver.findElement(By.id("hours")).getAttribute("value"));

        appointmentForm.setService(String.valueOf(webDriver.findElement(By.id("Service"))));

     webDriver.findElement(By.id("save")).click();
    }

    @Then("the request should be saved successfully")
    public void the_request_should_be_saved_successfully() {

    }

    @When("the user clicks on the {string} button1")
    public void the_user_clicks_on_the_button1(String Buy) {
        webDriver.findElement(By.id("by")).click();
    }

    @Then("the user should be on the home page")
    public void the_user_should_be_on_the_home_page() {
        webDriver.get("http://localhost:"+CucumberIT.getPort()+"/");

        webDriver.findElement(By.id("user_name")).sendKeys("toqa22");
        webDriver.findElement(By.id("pass")).sendKeys("666");
        sleep(2000);

        webDriver.findElement(By.id("LogInBtn")).click();

        sleep(6000);

    }


    @When("the user descide to buy a {string} from {string}")
    public void the_user_descide_to_buy_a_from(String string, String string2) {

    }

    @Then("the user click on {string} to see its information")
    public void the_user_click_on_to_see_its_information(String string) {

    }

    @Then("the user decides to buy it and click {string}")
    public void the_user_decides_to_buy_it_and_click(String string) {

    }

    @Then("the selected items remain in the cart")
    public void the_selected_items_remain_in_the_cart() {

    }

    @Given("the user has items in the cart")
    public void the_user_has_items_in_the_cart() {

    }

    @When("the user navigates to the checkout page")
    public void the_user_navigates_to_the_checkout_page() {

    }

    @Then("the user enters a valid discount code for a promotional offer")
    public void the_user_enters_a_valid_discount_code_for_a_promotional_offer() {

    }

    @Then("the user applies the discount to the total amount")
    public void the_user_applies_the_discount_to_the_total_amount() {

    }

    @Then("the user successfully completes the purchase with the discounted price")
    public void the_user_successfully_completes_the_purchase_with_the_discounted_price() {

    }

    @When("the user adds {string} and {string} to the cart")
    public void the_user_adds_and_to_the_cart(String string, String string2) {

    }

    @Then("the user views the cart to verify the selected items")
    public void the_user_views_the_cart_to_verify_the_selected_items() {

    }

    @Then("the user removes {string} from the cart")
    public void the_user_removes_from_the_cart(String string) {

    }

    @Then("the user proceeds to checkout with only {string}")
    public void the_user_proceeds_to_checkout_with_only(String string) {

    }

    @Given("the user is on the product information page for {string}")
    public void the_user_is_on_the_product_information_page_for(String string) {

    }

    @When("the user scrolls down to the customer reviews section")
    public void the_user_scrolls_down_to_the_customer_reviews_section() {

    }

    @Then("the user reads several reviews to make an informed decision")
    public void the_user_reads_several_reviews_to_make_an_informed_decision() {

    }

    @Then("the user adds {string} to the cart based on positive reviews")
    public void the_user_adds_to_the_cart_based_on_positive_reviews(String string) {

    }

    @Then("the user proceeds to checkout with the selected item")
    public void the_user_proceeds_to_checkout_with_the_selected_item() {

    }

    @When("the user decides not to proceed to checkout and leaves the page")
    public void the_user_decides_not_to_proceed_to_checkout_and_leaves_the_page() {

    }

    @Then("after a certain time, the user receives an email reminder about the abandoned cart")
    public void after_a_certain_time_the_user_receives_an_email_reminder_about_the_abandoned_cart() {

    }

    @Then("the email encourages the user to complete the purchase with a direct link to the cart")
    public void the_email_encourages_the_user_to_complete_the_purchase_with_a_direct_link_to_the_cart() {

    }


    private  void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
           logger.info("Erooooooooooooooooooooor");
        }
    }



}
