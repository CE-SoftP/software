package com.app.manegerAndProduct;
import com.app.customer.CustomerDb;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@SessionAttributes({"popupType", "popupMessage"})
public class ManegerController {
    Logger logger = Logger.getLogger(getClass().getName());
    private final ProductService  productService;
    private final String popUpTypeConst = "popupType";
    private final String popUpMessageConst = "popupMessage";
    private final String errorConst = "error";
    private final String successConst = "success";
    private final String productsConst = "products";
    private final String productConst = "product";
    private final String productListConst = "productList" ;
    private final String redirectCategoryConst = "redirect:/category/";
    private final String redirectHomeConst = "redirect:/home";


    @GetMapping("/model")
    public String showModel(Model model){


        return errorConst;
    }


private final ProductRepository productRepository;
ProductDb productDb=new ProductDb();
@Autowired

    public ManegerController(ProductService productService , ProductRepository productRepository) {
        this.productService = productService;
    this.productRepository=productRepository;
}

    @GetMapping("/category/{categoryId}")
    public String getProductFromCatagroies(@PathVariable int categoryId, Model model, HttpSession session) {
        List<ProductDb> productList =productRepository.findByCategoryId(categoryId);

        CustomerDb loggedInUser = (CustomerDb) session.getAttribute("loggedInUser");
        logger.info(loggedInUser.getRole());
        model.addAttribute("userRole",  loggedInUser.getRole() );
        model.addAttribute(productsConst, productList);
        return productConst;

    }


    @GetMapping("/product/{productId}")
    public String getProduct(@PathVariable int productId, Model model) {

        Optional<ProductDb> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            ProductDb product = productOptional.get();
            model.addAttribute(productConst, product);
        }
return productListConst;
    }

    @PostMapping("/add-product")
    public String addProduct(ProductInfo productInfo,Model model) {

    productDb=new ProductDb();
   String isAdd =productService.SaveProduct(productInfo,productDb);
 if (isAdd.equals("Product added successfully")){
     model.addAttribute(popUpTypeConst, successConst);
     model.addAttribute(popUpMessageConst, "Product added successfully");
     productRepository.save(productDb);
     Optional<ProductDb> productList1= productRepository.findById(productInfo.getProductId());
     if(productList1.isPresent()){
         ProductDb product=productList1.get();
         return redirectCategoryConst +product.getCategory().getId();
     }
     else
         return redirectHomeConst ;
 }

else {
     model.addAttribute(popUpTypeConst, errorConst);
     model.addAttribute(popUpMessageConst, "Product Name already exist!");

 }
        return redirectHomeConst;


    }


    @GetMapping("/select")
    public String yourMapping(Model model) {
        List<String> sections = productService.getAllCategories();
        List<ProductDb> products=productRepository.findAll();
        model.addAttribute("sections", sections);
        model.addAttribute(productsConst, products);
        return "Home";
    }





 @PostMapping("/add-catagroies")
    public String addCatagroies(@ModelAttribute CatagroiesForm catagroiesForm,Model model){
logger.info(catagroiesForm.getCataName());

     String isAdd =productService.SaveCatagroies(catagroiesForm);
     logger.info(isAdd);
     if (isAdd.equals("The Id already exist")){
         model.addAttribute(popUpTypeConst, errorConst);
         model.addAttribute(popUpMessageConst, "The Id already exist");

     }


   else if (isAdd.equals("Category already exists")){
         model.addAttribute(popUpTypeConst, errorConst);
         model.addAttribute(popUpMessageConst, "Category already exists");
     }
     else if (isAdd.equals("Category Name already exists")){
         model.addAttribute(popUpTypeConst, errorConst);
         model.addAttribute(popUpMessageConst, "Category Name already exists");
}

     return redirectHomeConst;

 }




 @GetMapping("/search/{productId}")
public String viewProduct(@PathVariable Long productId, Model model) {
     List<ProductDb> productList = productRepository.findAll();

     model.addAttribute(productsConst, productList);

     return productListConst;

}

    @PostMapping("/add-to-cart/{productId}")
    public String addToCart(@PathVariable int productId, Model model) {
        Optional<ProductDb> product=productRepository.findById(productId);
        ProductDb productList=product.get();
        if(product.isPresent()) {
            model.addAttribute(productConst, productList);
            String result = productService.addToCart(productId, 987);
            model.addAttribute("errorMessage", result);
        }

        return productListConst;
    }

    @GetMapping("/user/{userId}/card")
    public String showUserCardDetails(@PathVariable int userId, Model model, HttpSession session) {

        model.addAttribute("userId",userId);
        List<ProductDb> products = productService.getProductsByUserId(userId);
        model.addAttribute(productsConst, products);
        model.addAttribute("userId", userId);
        return "ShoppingList";
    }


    @PostMapping("/delete-product/{productId}")
    public String deleteProduct(@PathVariable int productId,Model model){
        Optional<ProductDb> productList1= productRepository.findById(productId);
        if(productList1.isPresent()) {
            ProductDb product = productList1.get();
            productService.deleteproduct(productId);
            model.addAttribute(popUpTypeConst, successConst);
            model.addAttribute(popUpMessageConst, "Product Deleted Successfully");
            return redirectCategoryConst + product.getCategory().getId();
        }
        return redirectHomeConst ;
    }
    @PostMapping("/update-product/{productId}")
    public String updateProduct(@PathVariable int productId,@ModelAttribute ProductInfo productInfo,Model model){

        productService.updateProduct(productId,productInfo);
        model.addAttribute(popUpTypeConst, successConst);
        model.addAttribute(popUpMessageConst, "Category Updated Successfully");
      Optional<ProductDb> productList1= productRepository.findById(productId);
      if(productList1.isPresent()){
          ProductDb product=productList1.get();
          return redirectCategoryConst+product.getCategory().getId();
      }
      return redirectHomeConst;

    }



    @PostMapping("/delete-categories/{id}")
    public String deleteCategories(@PathVariable String id,Model model){

      productService.deleteCategories(id);
        model.addAttribute(popUpTypeConst, successConst);
        model.addAttribute(popUpMessageConst, "Category Deleted Successfully");
        return redirectHomeConst;
    }

    @PostMapping("/search-product")
    public String searchProduct(@ModelAttribute ProductInfo productInfo,Model model){
        Optional<ProductDb> productOptional = productRepository.findByProductNameContainingIgnoreCase(productInfo.getProductName());

        if (productOptional.isPresent()) {
            ProductDb product = productOptional.get();
            model.addAttribute(productConst, product);
            return productListConst;
        }
        else {
            model.addAttribute("errorMessage", "The product not found");

        }
        return errorConst;
    }

}
