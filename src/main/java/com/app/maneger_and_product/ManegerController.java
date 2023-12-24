package com.app.maneger_and_product;
import com.app.customer.CustomerDb;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.support.SessionStatus;


import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@SessionAttributes({"popupType", "popupMessage"})
public class ManegerController {
    Logger logger = Logger.getLogger(getClass().getName());
    private final ProductService  productService;
    private static final String POPUP_TYPE = "popupType";
    private static final String POPUP_MESSAGE = "popupMessage";
    private static final String ERROR = "error";
    private static final String SUCCESS = "success";
    private static final String PRODUCTS = "products";
    private static final String PRODUCT = "product";
    private static final String PRODUCT_LIST = "productList" ;
    private static final String REDIRECT_CATEGORY = "redirect:/category/";
    private static final String REDIRECT_HOME = "redirect:/home";




private final ProductRepository productRepository;
ProductDb productDb=new ProductDb();
@Autowired

    public ManegerController(ProductService productService , ProductRepository productRepository) {
        this.productService = productService;
    this.productRepository=productRepository;
}

    @GetMapping("/category/{categoryId}")
    public String getProductFromCatagroies(@PathVariable int categoryId, Model model, HttpSession session , SessionStatus sessionStatus) {
        List<ProductDb> productList =productRepository.findByCategoryId(categoryId);

        CustomerDb loggedInUser = (CustomerDb) session.getAttribute("loggedInUser");
        logger.info(loggedInUser.getRole());
        model.addAttribute("userRole",  loggedInUser.getRole() );
        model.addAttribute(PRODUCTS, productList);
        sessionStatus.setComplete();
        return PRODUCT;

    }


    @GetMapping("/product/{productId}")
    public String getProduct(@PathVariable int productId, Model model) {

        Optional<ProductDb> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            ProductDb product = productOptional.get();
            model.addAttribute(PRODUCT, product);
        }
return PRODUCT_LIST;
    }

    @PostMapping("/add-product")
    public String addProduct(ProductInfo productInfo,Model model) {

    productDb=new ProductDb();
   String isAdd =productService.saveProduct(productInfo,productDb);
 if (isAdd.equals("Product added successfully")){
     model.addAttribute(POPUP_TYPE, SUCCESS);
     model.addAttribute(POPUP_MESSAGE, "Product added successfully");
     productRepository.save(productDb);
     Optional<ProductDb> productList1= productRepository.findById(productInfo.getProId());
     if(productList1.isPresent()){
         ProductDb product=productList1.get();
         return REDIRECT_CATEGORY +product.getCategory().getId();
     }
     else
         return REDIRECT_HOME;
 }

else {
     model.addAttribute(POPUP_TYPE, ERROR);
     model.addAttribute(POPUP_MESSAGE, "Product Name already exist!");

 }
        return REDIRECT_HOME;


    }




 @PostMapping("/add-catagroies")
    public String addCatagroies(@ModelAttribute CatagroiesForm catagroiesForm,Model model){

     String isAdd =productService.saveCatagroies(catagroiesForm);
     logger.info(isAdd);

      if (isAdd.equals("Category Name already exists")){
         model.addAttribute(POPUP_TYPE, ERROR);
         model.addAttribute(POPUP_MESSAGE, "Category Name already exists");
}

     return REDIRECT_HOME;

 }



    @PostMapping("/add-to-cart/{productId}")
    public String addToCart(@PathVariable int productId, Model model) {
        Optional<ProductDb> product=productRepository.findById(productId);
        if(product.isPresent()) {
            ProductDb productList=product.get();
            model.addAttribute(PRODUCT, productList);
            String result = productService.addToCart(productId, 987);
            model.addAttribute("errorMessage", result);
        }

        return PRODUCT_LIST;
    }


    @PostMapping("/delete-product/{productId}")
    public String deleteProduct(@PathVariable int productId,Model model){
        Optional<ProductDb> productList1= productRepository.findById(productId);
        if(productList1.isPresent()) {
            ProductDb product = productList1.get();
           String message= productService.deleteproduct(productId);
            model.addAttribute(POPUP_TYPE, SUCCESS);
            model.addAttribute(POPUP_MESSAGE, message);
            return REDIRECT_CATEGORY + product.getCategory().getId();
        }
        return REDIRECT_HOME;
    }
    @PostMapping("/update-product/{productId}")
    public String updateProduct(@PathVariable int productId,@ModelAttribute ProductInfo productInfo,Model model){

        productService.updateProduct(productId,productInfo);
        model.addAttribute(POPUP_TYPE, SUCCESS);
        model.addAttribute(POPUP_MESSAGE, "Category Updated Successfully");
      Optional<ProductDb> productList1= productRepository.findById(productId);
      if(productList1.isPresent()){
          ProductDb product=productList1.get();
           return REDIRECT_CATEGORY +product.getCategory().getId();
      }
       return REDIRECT_HOME;

    }



    @PostMapping("/delete-categories/{id}")
    public String deleteCategories(@PathVariable String id,Model model){
      productService.deleteCategories(id);
        model.addAttribute(POPUP_TYPE, SUCCESS);
        model.addAttribute(POPUP_MESSAGE, "Category Deleted Successfully");
        return REDIRECT_HOME;
    }

    @PostMapping("/search-product")
    public String searchProduct(@ModelAttribute ProductInfo productInfo,Model model){
        Optional<ProductDb> productOptional = productRepository.findByProductNameContainingIgnoreCase(productInfo.getProName());
        if (productOptional.isPresent()) {
            ProductDb product = productOptional.get();
            model.addAttribute(PRODUCT, product);
            return PRODUCT_LIST;
         }
         else {
            model.addAttribute("errorMessage", "The product not found");

        }
        return ERROR;
     }

    @PostMapping("/submitRating/{productId}")
    public String submitRating(@PathVariable int productId , int userRating) {
        ProductDb product = productRepository.findById(productId).orElse(null);
        if (product !=null) {
            int totalRatings = product.getAverageRating();

            int newTotalRatings = totalRatings + 1;
            int newAverageRating = (totalRatings * product.getAverageRating() + userRating) / newTotalRatings;

            product.setAverageRating(newAverageRating);
            productRepository.save(product);
        }

        return "redirect:/product/"+productId;
    }

}
