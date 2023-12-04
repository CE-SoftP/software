package com.app.ManegerAndProduct;
import com.app.customer.CustomerDb;
import com.app.customer.CustomerRepository;
import com.app.customer.DataForm;
import io.cucumber.core.logging.Logger;
import io.cucumber.messages.types.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
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
    public String getProductFromCatagroies(@PathVariable int categoryId, Model model) {
        List<ProductDb> productList =productRepository.findByCategoryId(categoryId);
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
    public String addProduct(ProductInfo productInfo) {
    productDb=new ProductDb();
   String isAdd =productService.SaveProduct(productInfo,productDb);
 if (isAdd.equals("Product added successfully")){

     productRepository.save(productDb);
 }



        return "product";


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


     }
     else if (isAdd.equals("The Name already exist")){


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
        List<ProductDb> productList = productRepository.findAll();
        model.addAttribute("products", productList);
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
    public String deleteProduct(@PathVariable int productId){
    productService.deleteproduct(productId);
return "signup";
    }
    @PostMapping("/update-product/{productId}")
    public String updateProduct(@PathVariable int productId,@ModelAttribute ProductInfo productInfo){

        productService.updateProduct(productId,productInfo);
        return "signup";
    }



    @PostMapping(" /delete-categories/{id}")
    public String deleteCategories(@PathVariable int id){
        productService.deleteCategories(id);

        return "signup";
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
