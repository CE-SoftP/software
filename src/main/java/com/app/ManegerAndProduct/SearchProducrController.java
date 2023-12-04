/*
package com.app.ManegerAndProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchProducrController {

    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<List<ProductDb>> searchProducts(@RequestParam String term) {
        List<ProductDb> products = productService.searchProducts(term);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
*/
