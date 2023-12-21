package com.app.ManegerAndProduct;
import com.app.customer.CustomerDb;
import com.app.customer.CustomerRepository;
import com.app.customer.DataForm;
import com.app.customer.DataService;
import io.cucumber.core.logging.Logger;
import io.cucumber.messages.types.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes({"popupType", "popupMessage"})
public class ManegerController {



    private CatagroiesForm catagroiesInfo;
    private final ProductService  productService;


    @GetMapping("/model")
    public String showModel(Model model){


        return "error";
    }


@Autowired
private ProductRepository productRepository;
ProductDb productDb=new ProductDb();
@Autowired

    public ManegerController(ProductService productService, CatagroisRepositary catagroisRepositary) {
        this.productService = productService;
    this.catagroisRepositary = catagroisRepositary;
}

    @GetMapping("/category/{categoryId}")
    public String getProductFromCatagroies(@PathVariable int categoryId, Model model, HttpSession session) {
        List<ProductDb> productList =productRepository.findByCategoryId(categoryId);

        CustomerDb loggedInUser = (CustomerDb) session.getAttribute("loggedInUser");
        System.out.println(loggedInUser.getRole());
        model.addAttribute("userRole",  loggedInUser.getRole() );
        model.addAttribute("products", productList);
        return "product";

    }


    @GetMapping("/product/{productId}")
    public String getProduct(@PathVariable int productId, Model model) {

        Optional<ProductDb> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            ProductDb product = productOptional.get();
            model.addAttribute("product", product);
        }
return "productList";
    }

    @PostMapping("/add-product")
    public String addProduct(ProductInfo productInfo,Model model) {

    productDb=new ProductDb();
   String isAdd =productService.SaveProduct(productInfo,productDb);
 if (isAdd.equals("Product added successfully")){
     model.addAttribute("popupType", "success");
     model.addAttribute("popupMessage", "Product added successfully");
     productRepository.save(productDb);
     Optional<ProductDb> productList1= productRepository.findById(productInfo.getProductId());
     ProductDb product=productList1.get();
     return "redirect:/category/"+product.getCategory().getId();
 }

else {
     model.addAttribute("popupType", "error");
     model.addAttribute("popupMessage", "Product Name already exist!");

 }
        return "redirect:/home";


    }

    private final CatagroisRepositary catagroisRepositary;


    @GetMapping("/select")
    public String yourMapping(Model model) {
        List<String> sections = productService.getAllCategories();
        List<ProductDb> products=productRepository.findAll();
        model.addAttribute("sections", sections);
        model.addAttribute("products", products);
        return "Home";
    }





 @PostMapping("/add-catagroies")
    public String addCatagroies(@ModelAttribute CatagroiesForm catagroiesForm,Model model){
System.out.println(catagroiesForm.getCataName());

     String isAdd =productService.SaveCatagroies(catagroiesForm);
     System.out.println(isAdd);
     if (isAdd.equals("The Id already exist")){
         model.addAttribute("popupType", "error");
         model.addAttribute("popupMessage", "The Id already exist");

     }


   else if (isAdd.equals("Category already exists")){
         model.addAttribute("popupType", "error");
         model.addAttribute("popupMessage", "Category already exists");
     }
     else if (isAdd.equals("Category Name already exists")){
         model.addAttribute("popupType", "error");
         model.addAttribute("popupMessage", "Category Name already exists");
}

     return "redirect:/home";

 }




 @GetMapping("/search/{productId}")
public String viewProduct(@PathVariable Long productId, Model model) {
     List<ProductDb> productList = productRepository.findAll();

     model.addAttribute("products", productList);

     return "productList";

}

    @PostMapping("/add-to-cart/{productId}")
    public String addToCart(@PathVariable int productId, Model model) {
        Optional<ProductDb> product=productRepository.findById(productId);
      ProductDb productList=product.get();
        model.addAttribute("product", productList);
       // int userId = (int) session.getAttribute("userId");
        String result=productService.addToCart(productId,987);
        model.addAttribute("errorMessage", result);


        return "productList";
    }

    @GetMapping("/user/{userId}/card")
    public String showUserCardDetails(@PathVariable int userId, Model model, HttpSession session) {

        model.addAttribute("userId",userId);
        List<ProductDb> products = productService.getProductsByUserId(userId);
        model.addAttribute("products", products);
        model.addAttribute("userId", userId);
        return "ShoppingList";
    }


    @PostMapping("/delete-product/{productId}")
    public String deleteProduct(@PathVariable int productId,Model model){
        Optional<ProductDb> productList1= productRepository.findById(productId);
        ProductDb product=productList1.get();
        productService.deleteproduct(productId);
        model.addAttribute("popupType", "success");
        model.addAttribute("popupMessage", "Product Deleted Successfully");
        return "redirect:/category/"+product.getCategory().getId();

    }
    @PostMapping("/update-product/{productId}")
    public String updateProduct(@PathVariable int productId,@ModelAttribute ProductInfo productInfo,Model model){

        productService.updateProduct(productId,productInfo);
        model.addAttribute("popupType", "success");
        model.addAttribute("popupMessage", "Category Updated Successfully");
      Optional<ProductDb> productList1= productRepository.findById(productId);
      ProductDb product=productList1.get();



    return "redirect:/category/"+product.getCategory().getId();


    }



    @PostMapping("/delete-categories/{id}")
    public String deleteCategories(@PathVariable String id,Model model){

      productService.deleteCategories(id);
        model.addAttribute("popupType", "success");
        model.addAttribute("popupMessage", "Category Deleted Successfully");
        return "redirect:/home";
    }

    @PostMapping("/search-product")
    public String searchProduct(@ModelAttribute ProductInfo productInfo,Model model){
        Optional<ProductDb> productOptional = productRepository.findByProductNameContainingIgnoreCase(productInfo.getProductName());

        if (productOptional.isPresent()) {
            ProductDb product = productOptional.get();
            model.addAttribute("product", product);
            return "productList";
        }
        else {
            model.addAttribute("errorMessage", "The product not found");

        }
        return "error";
    }

}
