package StepDefinitions;

import com.app.installation.InstallationDB;
import com.app.installation.InstallationService;
import com.app.customer.CustomerDb;
import com.app.customer.CustomerService;
import com.app.order.OrderDatabase;
import com.app.order.OrderService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class notificationStep {
    @Autowired
    private InstallationService installationService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService OrderService;

    boolean popUp;
    int count ;
    @Autowired
    private WebDriver webDriver;
    private List<OrderDatabase> orders;
    String userName;
    List<InstallationDB> installations ;
    List<InstallationDB> AdminInstallations;


    @When("I logged in as a customer with name {string} and password {string}")
    public void i_logged_in_as_a_customer_with_name_and_password(String name, String pass) {
        webDriver.get("http://localhost:"+CucumberIT.getPort()+"/");

        webDriver.findElement(By.id("user_name")).sendKeys(name);
        webDriver.findElement(By.id("pass")).sendKeys(pass);

        userName=name;
        webDriver.findElement(By.id("LogInBtn")).click();
    }


    @When("I have order confirmation")
    public void i_have_order_confirmation() {
        CustomerDb user = customerService.findByUsername(userName);
        orders = OrderService.getOrderByPopUpUser("NO" , user.getId());
        System.out.println("size = " + orders.size());
    }

    @When("I have installation updates")
    public void i_have_installation_updates() {
        CustomerDb user = customerService.findByUsername(userName);
        installations = installationService.getInstallationsByCheckedUserAndCustomerId("NO", user.getId());
        System.out.println("size = " + installations.size());
    }

    @Then("a pop up for {string} will be shown")
    public void a_pop_up_for_will_be_shown(String type) {
        if(type.equals("order")){
            for(OrderDatabase order : orders){
                System.out.println("id" + order.getId());
                Alert alert = webDriver.switchTo().alert();
                String actualMessage = alert.getText();
                alert.accept();
                String expectedMessage = "Success: Yor order with id : " + order.getId() + "have been confirmed";
                assertEquals(expectedMessage, actualMessage);
            }
        } else if (type.equals("installation")) {
            Alert alert = webDriver.switchTo().alert();
            String actualMessage = alert.getText();
            alert.accept();
            String expectedMessage = "Success: You have " + installations.size() + " Requests to check";
            assertEquals(expectedMessage, actualMessage);


        }
    }

    @When("I have no notification to see")
    public void i_have_no_notification_to_see() {
        CustomerDb user = customerService.findByUsername(userName);
        installations = installationService.getInstallationsByCheckedUserAndCustomerId("NO", user.getId());
        orders = OrderService.getOrderByPopUpUser("NO" , user.getId());
        if(orders.size()==0 && installations.size()==0)
            popUp=false;

    }

    @Then("i should be redirected to the home page")
    public void i_should_be_redirected_to_the_home_page() {
        if(!popUp){
            String title =webDriver.getTitle();
            assertEquals("Home page",title);
        }
    }

    @When("I logged in as an installer with name {string} and password {string}")
    public void i_logged_in_as_an_installer_with_name_and_password(String name, String pass) {
        webDriver.get("http://localhost:"+CucumberIT.getPort()+"/");

        webDriver.findElement(By.id("user_name")).sendKeys(name);
        webDriver.findElement(By.id("pass")).sendKeys(pass);
userName=name;

        webDriver.findElement(By.id("LogInBtn")).click();
    }

    @When("I have installation requests")
    public void i_have_installation_requests() {
        CustomerDb user = customerService.findByUsername(userName);
        AdminInstallations = installationService.getInstallationsByCheckedAdmin("NO");
    }

    @Then("a pop up for {string} will be shown or i will be redirected to the home page if there is no requests")
    public void a_pop_up_for_will_be_shown_or_i_will_be_redirected_to_the_home_page_if_there_is_no_requests(String string) {
        if(AdminInstallations.size()==0){
            String title =webDriver.getTitle();
            assertEquals("Home page",title);
        }else{
            System.out.println("size = "+ AdminInstallations.size());
            Alert alert = webDriver.switchTo().alert();
            String actualMessage = alert.getText();
            alert.accept();
            String expectedMessage = "Success: You have " + AdminInstallations.size() + " Requests to check";
            assertEquals(expectedMessage, actualMessage);

        }
    }

    @When("I logged in as an admin with name {string} and password {string}")
    public void i_logged_in_as_an_admin_with_name_and_password(String name, String pass) {

        webDriver.get("http://localhost:"+CucumberIT.getPort()+"/");

        webDriver.findElement(By.id("user_name")).sendKeys(name);
        webDriver.findElement(By.id("pass")).sendKeys(pass);
userName=name;

        webDriver.findElement(By.id("LogInBtn")).click();
    }

}