package com.app.ManegerAndProduct;

import com.app.customer.CustomerDb;
import com.app.customer.CustomerRepository;
import com.app.customer.DataForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/*import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service

public class ProductService {

    CardDb cardDb=new CardDb();
    @Autowired
    CatagroisRepositary catagroisRepositary;
    @Autowired
    CatagroisRepositary catagroiesRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CardRepository cardRepository;
    ProductDb productDb;

    Catagroies catagroies;
    @Autowired
    ProductRepository productRepository;
    public String SaveProduct(ProductInfo productInfo,ProductDb productDb) {


        Catagroies catagroies1=catagroiesRepository.findByName(productInfo.getSection());
        boolean exist = productRepository.existsById(productInfo.getProductId());
        if (!exist) {

            productDb.setProductId(productInfo.getProductId());
            productDb.setProductName(productInfo.getProductName());
            productDb.setPrice(productInfo.getPrice());
            productDb.setSection(productInfo.getSection());
            productDb.setNumberOf(productInfo.getNumberOf());
            productDb.setImage(productInfo.getImage());
            productDb.setInformation(productInfo.getInformation());
            productDb.setCategory(catagroies1);

            return "Product added successfully";
        }
        else
            return "Product already exist!";
    }


    public List<String> getAllCategories() {
        return catagroiesRepository.findDistinctCategories();
    }


    public String SaveCatagroies( CatagroiesForm catagroiesForm) {


        boolean exist = catagroiesRepository.existsById( catagroiesForm.getCataId());
        System.out.println(exist);
        boolean nameExist=catagroiesRepository.existsByName(catagroiesForm.getCataName());

        if(exist){
            return "Category already exists";
        }

        else if(nameExist){
            return "Category Name already exists";
        }

       else if (!exist) {
            System.out.println("llll");
            catagroies=new Catagroies();
            catagroies.setId(catagroiesForm.getCataId());
            catagroies.setName(catagroiesForm.getCataName());
            catagroies.setImageUrl(catagroiesForm.getImage());
            catagroies.setCategory(catagroiesForm.getCataName());
            catagroiesRepository.save(catagroies);
            return "Category added successfully";
        }


        return "The Id already exist";

}



public String addToCart(int productId,int userId) {

    Optional<ProductDb> productOptional = productRepository.findById(productId);
    List<CardDb> order = cardRepository.findByCustomerDbId(userId);
    int totalPrice = 0;
    for (CardDb card : order) {
        totalPrice += card.getTotalPrice();
    }

    if (productOptional.isPresent()) {
        ProductDb product = productOptional.get();
        totalPrice += product.getPrice();
        if (totalPrice <= 1000) {
            int number = product.getNumberOf();
            if (number == 0) {
                return "the product not available for now";
            }
            number -= 1;
            product.setNumberOf(number);
            if (number == 0) {
                product.setAvailable("false");
            }

            productRepository.save(product);
            Optional<CustomerDb> customerDbOptional = customerRepository.findById(userId);
            CustomerDb customer = customerDbOptional.get();
            CardDb cartItem = new CardDb();
            cartItem.setProductDb(product);
            cartItem.setCustomerDb(customer);
            cartItem.setTotalPrice(product.getPrice());
            cartItem.setCardId(2);

            cardRepository.save(cartItem);

            return "product is added successfully";
        }

        return "The Cart is full";

}
    return "the product not available for now ";}


    public List<ProductDb> getProductsByUserId(int userId) {
        List<CardDb> cards = cardRepository.findByCustomerDbId(userId);
        List<ProductDb> products = new ArrayList<>();

        for (CardDb card : cards) {
            products.add(card.getProductDb());
        }

        return products;
    }

    public String deleteproduct(int productId){

        Optional<ProductDb> productDb1= productRepository.findById(productId);
        ProductDb productDb2=productDb1.get();
   productRepository.deleteAllById(Collections.singleton(productDb2.getProductId()));

    return "delete successfully";



}



    public String updateProduct(int id, ProductInfo productInfo){
        Catagroies catagroies1=catagroiesRepository.findByName(productInfo.getSection());

        Optional<ProductDb> optionalProduct = productRepository.findById(id);
        ProductDb Product=optionalProduct.get();
        Product.setProductId(productInfo.getProductId());
        Product.setProductName(productInfo.getProductName());
        Product.setPrice(productInfo.getPrice());
        Product.setSection(productInfo.getSection());
        Product.setNumberOf(productInfo.getNumberOf());
        Product.setImage(productInfo.getImage());
        Product.setInformation(productInfo.getInformation());
        Product.setCategory(catagroies1);
      productRepository.save(Product);
        return "hi ";
    }

    public void deleteCategories(String id) {
       Catagroies deleteCatagory= catagroisRepositary.findByName(id);
        catagroisRepositary.deleteAllById(Collections.singleton(deleteCatagory.getId()));


    }
}