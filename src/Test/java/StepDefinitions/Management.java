package StepDefinitions;

import com.app.ManegerAndProduct.*;

import com.app.customer.CustomerDb;
import com.app.customer.DataForm;
import com.app.customer.DataService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;


public class Management {
    ProductDb productDb=new ProductDb();
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    ProductService productService;
    String result;
    @Autowired
    private ManegerController manegerController;
    ProductInfo productInfo=new ProductInfo();
    @Autowired
    WebDriver webDriver;
    @Autowired
    DataService dataService;

    CatagroiesForm catagroiesForm=new CatagroiesForm();

    private DataForm dataForm=new DataForm();
    private LogInSteps logInSteps=new LogInSteps();
    private CustomerDb customerDb=new CustomerDb();
    @Given("the user is on the Admin page")
    public void the_user_is_on_the_admin_page() {
        webDriver.get("http://localhost:"+CucumberIT.getPort()+"/");

        webDriver.findElement(By.id("user_name")).sendKeys("eman");
        webDriver.findElement(By.id("pass")).sendKeys("555");
        sleep(2000);

        webDriver.findElement(By.id("LogInBtn")).click();

         sleep(6000);

    }

    @When("the admin clicks on Management")
    public void theAdminClicksOnManagement() {
     webDriver.findElement(By.id("management")).click();

    }
    @Then("a form should appear")
    public void aFormShouldAppear() {
      Boolean Form=  webDriver.findElement(By.id("addProductForm")).isDisplayed();
      Assertions.assertTrue(Form);

    }

    @Then("the {string} section should appear with delete button")
    public void theSectionShouldAppearWithDeleteButton(String AddCatagries) {
       boolean delete = webDriver.findElement(By.id("deleteButton")).isDisplayed();
       boolean Add= webDriver.findElement(By.id("addNewButton")).isDisplayed();
       boolean result=(delete & Add);
       Assertions.assertTrue(result);
    }

    @When("the admin clicks on Add Category")
    public void theAdminClicksOnAddCategory() {
        webDriver.findElement(By.id("addNewButton")).click();
    }
    @And("the admin fills in the category details: Category Name {string}")
    public void theAdminFillsInTheCategoryDetailsCategoryName(String catagryName) {

        webDriver.findElement(By.id("categorytId")).sendKeys("1285");
        webDriver.findElement(By.id("categoryName")).sendKeys(catagryName);
        catagroiesForm.setCataName(catagryName);
    }

    @And("the manager submits the Add Categories form")
    public void theManagerSubmitsTheAddCategoriesForm() {

        webDriver.findElement(By.id("add categories")).click();

    }
    @Then("the system should display a success save categories message: {string}")
    public void theSystemShouldDisplayASuccessSaveCategoriesMessage(String massege) {
      String actual=  productService.SaveCatagroies(catagroiesForm);
        assertEquals(massege,actual);

    }
    @Then("the system should display an error message: {string}")
    public void theSystemShouldDisplayAnErrorMessage(String massege) {
        String actual=  productService.SaveCatagroies(catagroiesForm);
        assertEquals(massege,actual);
    }
    @Then("the Add product form should appear")
    public void the_add_product_form_should_appear() {
        //assertTrue(webDriver.findElement(By.id("addProductForm")).isDisplayed());
    }

    @When("the admin clicks on {string} category")
    public void theAdminClicksOnCategory(String ExteriorAccesoris) {
        webDriver.findElement(By.id("Exterior Accessories")).click();

    }

    @Then("the admin should be in a {string} product page")
    public void theAdminShouldBeInAProductPage(String page) {
    //assertEquals(webDriver.getTitle(),page);
    }

    @Then("the {string} section should appear with delete and update buttons")
    public void theSectionShouldAppearWithDeleteAndUpdateButtons(String arg0) {
        boolean delete = webDriver.findElement(By.id("deleteButton")).isDisplayed();
        boolean update = webDriver.findElement(By.id("updateButtons")).isDisplayed();
        boolean Add= webDriver.findElement(By.id("addNewButton")).isDisplayed();
        boolean result=(delete & Add & update);
        Assertions.assertTrue(result);
    }

    @When("the admin clicks on Add Product")
    public void theAdminClicksOnAddProduct() {
        webDriver.findElement(By.id("addNewButton")).click();
    }
    @Then("the manager submits the form")
    public void the_manager_submits_the_form() {

    webDriver.findElement(By.id("add_pro")).click();
    sleep(2000);
    }

    @Then("the system should display a success message: {string}")
    public void the_system_should_display_a_success_message(String string) {


        result=productService.SaveProduct(productInfo,productDb);
        assertEquals(result,"Product added successfully");

sleep(2000);

    }
    @And("the admin fills in the product details: Product ID {string}, Product Name {string}, Information {string}, Price {string}, Section {string}, Number {string}, Image {string}")
    public void theAdminFillsInTheProductDetailsProductIDProductNameInformationPriceSectionNumberImage(String productId, String productName, String info,String price, String section, String number, String arg6) {

        webDriver.findElement(By.id("productId")).sendKeys(productId);
        webDriver.findElement(By.id("productName")).sendKeys(productName);
        webDriver.findElement(By.id("information")).sendKeys(info);
        webDriver.findElement(By.id("price")).sendKeys(price);

        WebElement serviceSelect = webDriver.findElement(By.id("section"));
        Select select2 = new Select(serviceSelect);
        select2.selectByVisibleText(section);
        webDriver.findElement(By.id("numberOf")).sendKeys(number);

        productInfo.setProductId(Integer.parseInt(webDriver.findElement(By.id("productId")).getAttribute("value")));
        productInfo.setProductName(webDriver.findElement(By.id("productName")).getAttribute("value"));
        productInfo.setInformation(webDriver.findElement(By.id("information")).getAttribute("value"));
        productInfo.setPrice(Integer.parseInt(webDriver.findElement(By.id("price")).getAttribute("value")));
        productInfo.setSection(webDriver.findElement(By.id("section")).getAttribute("value"));
        productInfo.setSection(webDriver.findElement(By.id("numberOf")).getAttribute("value"));
        productInfo.setImage(webDriver.findElement(By.id("image")).getAttribute("value"));
        sleep(2000);

    }
    @Then("the added product details should be visible in the product list")
    public void the_added_product_details_should_be_visible_in_the_product_list() {
        /*productInfo.setProductId(Integer.parseInt(webDriver.findElement(By.id("productId")).getAttribute("value")));
        productInfo.setProductName(webDriver.findElement(By.id("productName")).getAttribute("value"));
        productInfo.setInformation(webDriver.findElement(By.id("information")).getAttribute("value"));
        productInfo.setPrice(Integer.parseInt(webDriver.findElement(By.id("price")).getAttribute("value")));
        productInfo.setSection(webDriver.findElement(By.id("section")).getAttribute("value"));
        productInfo.setSection(webDriver.findElement(By.id("numberOf")).getAttribute("value"));
        productInfo.setImage(webDriver.findElement(By.id("image")).getAttribute("value"));

        String isAdd=productService.SaveProduct(productInfo);
        assertEquals(isAdd,"Product added successfully");*/


    }
    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {

        }
    }


    @And("the admin fills in the product details with new product : Product ID {string}, Product Name {string}, Information {string}, Price {string}, Section {string}, Number {string}, Image {string}")
    public void theAdminFillsInTheProductDetailsWithNewProductProductIDProductNameInformationPriceSectionNumberImage(String productId, String productName, String info,String price, String section, String number, String arg6) {
        webDriver.findElement(By.id("productId")).sendKeys(productId);
        webDriver.findElement(By.id("productName")).sendKeys(productName);
        webDriver.findElement(By.id("information")).sendKeys(info);
        webDriver.findElement(By.id("price")).sendKeys(price);

        WebElement serviceSelect = webDriver.findElement(By.id("section"));
        Select select2 = new Select(serviceSelect);
        select2.selectByVisibleText(section);
        webDriver.findElement(By.id("numberOf")).sendKeys(number);

        productInfo.setProductId(Integer.parseInt(webDriver.findElement(By.id("productId")).getAttribute("value")));
        productInfo.setProductName(webDriver.findElement(By.id("productName")).getAttribute("value"));
        productInfo.setInformation(webDriver.findElement(By.id("information")).getAttribute("value"));
        productInfo.setPrice(Integer.parseInt(webDriver.findElement(By.id("price")).getAttribute("value")));
        productInfo.setSection(webDriver.findElement(By.id("section")).getAttribute("value"));
        productInfo.setSection(webDriver.findElement(By.id("numberOf")).getAttribute("value"));
        productInfo.setImage(webDriver.findElement(By.id("image")).getAttribute("value"));
        sleep(2000);
    }

    @When("the admin clicks on Update Product")
    public void theAdminClicksOnUpdateProduct() {
        webDriver.findElement(By.id("updateButtons")).click();
sleep(2000);
    }



    @When("the admin clicks on Delete Product")
    public void the_admin_clicks_on_delete_product() {
        webDriver.findElement(By.id("deleteButton")).click();

    }

    @Then("an alert should appear asking for confirmation")
    public void an_alert_should_appear_asking_for_confirmation() {
       boolean result= webDriver.findElement(By.id("deleteConfirmation")).isDisplayed();
       assertTrue(result);
    }

    @When("the admin clicks on Yes")
    public void the_admin_clicks_on_yes() {
        webDriver.findElement(By.id("deleteConfirmationYes")).click();
    }

    @Then("the product should be deleted successfully")
    public void the_product_should_be_deleted_successfully() {
        boolean result= webDriver.findElement(By.id("deleteConfirmation")).isDisplayed();
        assertTrue(result);
    }


    @Then("a form should appear with the previous product data")
    public void a_form_should_appear_with_the_previous_product_data() {
        boolean result= webDriver.findElement(By.id("updateForm")).isDisplayed();
        assertTrue(result);
        sleep(2000);
    }


    @And("the admin fills in the updated product details: Product Name {string}, Information {string}, Price {string}, Section {string}, Number {string}, Image {string}")
    public void theAdminFillsInTheUpdatedProductDetailsProductNameInformationPriceSectionNumberImage(String productId, String productName, String info, String price, String section, String number) {

        webDriver.findElement(By.id("productName")).sendKeys(productName);
        webDriver.findElement(By.id("information")).sendKeys(info);
        webDriver.findElement(By.id("price")).sendKeys(price);

        WebElement serviceSelect = webDriver.findElement(By.id("section"));
        Select select2 = new Select(serviceSelect);
        select2.selectByVisibleText(section);
        webDriver.findElement(By.id("numberOf")).sendKeys(number);

        productInfo.setProductId(Integer.parseInt(webDriver.findElement(By.id("productId")).getAttribute("value")));
        productInfo.setProductName(webDriver.findElement(By.id("productName")).getAttribute("value"));
        productInfo.setInformation(webDriver.findElement(By.id("information")).getAttribute("value"));
        productInfo.setPrice(Integer.parseInt(webDriver.findElement(By.id("price")).getAttribute("value")));
        productInfo.setSection(webDriver.findElement(By.id("section")).getAttribute("value"));
        productInfo.setSection(webDriver.findElement(By.id("numberOf")).getAttribute("value"));
        productInfo.setImage(webDriver.findElement(By.id("image")).getAttribute("value"));
        sleep(2000);
    }

    @And("the manager submits the update form")
    public void theManagerSubmitsTheUpdateForm() {
        webDriver.findElement(By.id("updateButton"));
        sleep(2000);
    }

    @When("the admin clicks on No or Cancel")
    public void theAdminClicksOnNoOrCancel() {
        webDriver.findElement(By.id("cancelDelete")).click();
    }


    @Then("the product should not be deleted")
    public void theProductShouldNotBeDeleted() {
    }

    @When("the admin clicks on Delete Category")
    public void the_admin_clicks_on_delete_category() {

    }

    @Then("the category should be deleted successfully")
    public void the_category_should_be_deleted_successfully() {

    }

    @Then("the category should not be visible on the home page")
    public void the_category_should_not_be_visible_on_the_home_page() {

    }




    @When("the admin clicks on No")
    public void the_admin_clicks_on_no() {

    }

    @Then("the category should still be visible on the home page")
    public void the_category_should_still_be_visible_on_the_home_page() {

    }

}
