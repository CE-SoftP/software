package StepDefinitions;

import com.app.maneger_and_product.*;

import com.app.customer.CustomerDb;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static junit.framework.TestCase.assertEquals;


public class Management {
    ProductDb productDb=new ProductDb();

    @Autowired
    ProductService productService;
    String result;

    ProductInfo productInfo=new ProductInfo();
    @Autowired
    WebDriver webDriver;
 

    CatagroiesForm catagroiesForm=new CatagroiesForm();



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
       boolean delete = webDriver.findElement(By.className("deleteButton")).isDisplayed();
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

    @Then("the system should display an error message: {string}")
    public void theSystemShouldDisplayAnErrorMessage(String massege) {

        Alert alert = webDriver.switchTo().alert();
        String actualMessage = alert.getText();


        alert.accept();

        Assert.assertEquals(massege, actualMessage);

    }
    @Then("the Add product form should appear")
    public void the_add_product_form_should_appear() {
        //assertTrue(webDriver.findElement(By.id("addProductForm")).isDisplayed());
    }

    @When("the admin clicks on {string} category")
    public void theAdminClicksOnCategory(String ExteriorAccesoris) {

        WebElement element = webDriver.findElement(By.id(ExteriorAccesoris));
        element.click();


    }

    @Then("the admin should be in a {string} product page")
    public void theAdminShouldBeInAProductPage(String page) {
    //assertEquals(webDriver.getTitle(),page);
    }

    @Then("the {string} section should appear with delete and update buttons")
    public void theSectionShouldAppearWithDeleteAndUpdateButtons(String arg0) {
        boolean delete = webDriver.findElement(By.className("deleteButton")).isDisplayed();
        boolean update = webDriver.findElement(By.className("updateButton")).isDisplayed();
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
    public void the_system_should_display_a_success_message(String expectedMessage) {

        Alert alert = webDriver.switchTo().alert();
        String actualMessage = alert.getText();


        alert.accept();

        Assert.assertEquals(expectedMessage, actualMessage);


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
productInfo.setProductId(Integer.parseInt(webDriver.findElement(By.id("productId")).getAttribute("value")));
        productInfo.setProductName(webDriver.findElement(By.id("productName")).getAttribute("value"));
        productInfo.setInformation(webDriver.findElement(By.id("information")).getAttribute("value"));
        productInfo.setPrice(Integer.parseInt(webDriver.findElement(By.id("price")).getAttribute("value")));
        productInfo.setSection(webDriver.findElement(By.id("section")).getAttribute("value"));
        productInfo.setSection(webDriver.findElement(By.id("numberOf")).getAttribute("value"));
        productInfo.setImage(webDriver.findElement(By.id("image")).getAttribute("value"));

        String isAdd=productService.saveProduct(productInfo,productDb);
        assertEquals(isAdd,"Product added successfully");



    }
    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } 
        catch (Exception e) {
           System.out.println("errror");
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







    @Then("an alert should appear asking for confirmation Delete {string}")
    public void anAlertShouldAppearAskingForConfirmationDelete(String category) {
        boolean result= webDriver.findElement(By.id("deleteConfirmation "+category)).isDisplayed();
        assertTrue(result);
        sleep(2000);
    }



    @When("the admin clicks on Yes to delete {string}")
    public void theAdminClicksOnYesToDelete(String name) {
          webDriver.findElement(By.id("deleteConfirmationYes "+name)).click();
            sleep(2000);
        }

    @Then("the product should be deleted successfully")
    public void the_product_should_be_deleted_successfully() {
        boolean result= webDriver.findElement(By.id("deleteConfirmation")).isDisplayed();
        assertTrue(result);
    }


    @Then("a form should appear with the previous product data")
    public void a_form_should_appear_with_the_previous_product_data() {
        boolean result= webDriver.findElement(By.id("updateForm")).isDisplayed();
        webDriver.findElement(By.id("updateProductName")).sendKeys("");
        assertTrue(result);
        sleep(2000);
    }


    @And("the admin fills in the updated product details: Product Name {string}, Information {string}, Price {string}, Section {string}, Number {string}, Image {string}")
    public void theAdminFillsInTheUpdatedProductDetailsProductNameInformationPriceSectionNumberImage(String productName, String info, String price, String section, String number, String img) {
        webDriver.findElement(By.id("updateProductName")).clear();
        webDriver.findElement(By.id("updateProductName")).sendKeys(productName);
        webDriver.findElement(By.id("updateInformation")).clear();
        webDriver.findElement(By.id("updateInformation")).sendKeys(info);
        webDriver.findElement(By.id("updatePrice")).sendKeys(price);

        WebElement serviceSelect = webDriver.findElement(By.id("updateSection"));
        Select select2 = new Select(serviceSelect);
        select2.selectByVisibleText(section);
        webDriver.findElement(By.id("updateNumberOf")).sendKeys(number);


        productInfo.setProductName(webDriver.findElement(By.id("updateProductName")).getAttribute("value"));
        productInfo.setInformation(webDriver.findElement(By.id("updateInformation")).getAttribute("value"));
        productInfo.setPrice(Integer.parseInt(webDriver.findElement(By.id("updatePrice")).getAttribute("value")));
        productInfo.setSection(webDriver.findElement(By.id("updateSection")).getAttribute("value"));
        productInfo.setSection(webDriver.findElement(By.id("updateNumberOf")).getAttribute("value"));

        sleep(2000);
    }

    @When("the admin clicks on No or Cancel")
    public void theAdminClicksOnNoOrCancel() {
        webDriver.findElement(By.id("cancelDelete")).click();
    }


    @Then("the product should not be deleted")
    public void theProductShouldNotBeDeleted() {

    }

 



  








    @And("the Catagory {string} should add successfully")
    public void theCatagoryShouldAddSuccessfully(String catagoryName) {
        Boolean isDisplay=webDriver.findElement(By.id(catagoryName)).isDisplayed();
        assertTrue(isDisplay);

    }

    @Then("the system should display an success message: {string}")
    public void theSystemShouldDisplayAnSuccessMessage(String massege) {
        Alert alert = webDriver.switchTo().alert();
        String actualMessage = alert.getText();


        alert.accept();

        Assert.assertEquals(massege, actualMessage);
    }

    @When("the admin clicks on Delete {string} Category")
    public void theAdminClicksOnDeleteCategory(String categoryName) {

        webDriver.findElement(By.id("deleteButton "+ categoryName)).click();
        sleep(200);

    }


    @When("the admin clicks on No to cancale delete {string}")
    public void theAdminClicksOnNoToCancaleDelete(String name) {
        webDriver.findElement(By.id("deleteConfirmationNo "+name)).click();
        sleep(2000);
    }

    @Then("the category {string} should still be visible on the home page")
    public void theCategoryShouldStillBeVisibleOnTheHomePage(String name) {
        Boolean isDisplay =webDriver.findElement(By.id(name)).isDisplayed();
        assertTrue(isDisplay);

    }

    @And("the manager click on {string} button")
    public void theManagerClickOnButton(String button) {
        webDriver.findElement(By.id(button));
    }

    @Then("the form should disappear")
    public void theFormShouldDisappear() {
        Boolean Form=  webDriver.findElement(By.id("addProductForm")).isDisplayed();
        Assertions.assertFalse(Form);
    }

    @When("the admin clicks on Delete {string} Product")
    public void theAdminClicksOnDeleteProduct(String productName) {
        webDriver.findElement(By.id("deleteButton "+ productName)).click();
        sleep(200);
    }

    @When("the admin clicks on Update {string} Product")
    public void theAdminClicksOnUpdateProduct(String productName) {
        String updateButtonId = "updateButtons " + productName;

        Duration duration =Duration.ofSeconds(10);
        WebElement updateButton = new WebDriverWait(webDriver, duration)
                .until(ExpectedConditions.elementToBeClickable(By.id(updateButtonId)));


        updateButton.click();

        sleep(200);
    }

    @And("the manager click on update to Sumbit the form and {string} be updated")
    public void theManagerClickOnUpdateToSumbitTheFormAndBeUpdated(String productName) {

        String updateButtonId = "update" + productName;

        Duration duration =Duration.ofSeconds(10);
        WebElement updateButton = new WebDriverWait(webDriver, duration)
                .until(ExpectedConditions.elementToBeClickable(By.id(updateButtonId)));


        updateButton.click();

        sleep(200);


    }

    @Then("admin clicks on Delete {string} Product")
    public void adminClicksOnDeleteProduct(String productName) {
        webDriver.findElement(By.id("deleteButton "+ productName)).click();
        sleep(200);
    }

    @Then("the product {string} should still be visible on the home page")
    public void theProductShouldStillBeVisibleOnTheHomePage(String name) {
        Boolean isDisplay =webDriver.findElement(By.id(name)).isDisplayed();
        assertTrue(isDisplay);
    }

    @And("the admin clicks on Cancel")
    public void theAdminClicksOnCancel() {
        webDriver.findElement(By.id("closeFormButton")).click();
        sleep(200);

    }



    @Then("The Add form should disappear")
    public void theAddFormShouldDisappear() {
        Boolean Form=  webDriver.findElement(By.id("addProductForm")).isDisplayed();
        Assertions.assertFalse(Form);
    }

    @And("the admin clicks on Cancel Update")
    public void theAdminClicksOnCancelUpdate() {
        webDriver.findElement(By.id("CancelUpdate")).click();
        sleep(200);
    }


    @Then("the form should be closed")
    public void theFormShouldBeClosed() {
        boolean result= webDriver.findElement(By.id("updateForm")).isDisplayed();
        assertFalse(result);
        sleep(2000);
    }

    @And("the admin keep in the home page")
    public void theAdminKeepInTheHomePage() {
       String title= webDriver.getTitle();
       assertEquals(title,"Home page");

    }
}
