package com.project.FERMS.controllers;

import com.project.FERMS.models.ProductData;
import com.project.FERMS.services.ProductDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/product")
public class ProductDataController {

    @Autowired
    private final ProductDataService productDataService;

    @PostMapping(path = "/register")
    public ProductData registerNewProductData(@RequestBody ProductData productData){
        return productDataService.addProductData(productData);
    }

    @GetMapping
    public List<ProductData> displayProductData() {
        return productDataService.displayProductData();
    }

    @GetMapping(path = "/display/{id}")
    public ProductData displayProductById(@PathVariable Integer id) {
        return productDataService.displayProductById(id);
    }

    @PutMapping(path = "/update/{id}")
    public ProductData updateProduct(@PathVariable Integer id,@RequestBody ProductData productData) {
//        productData.setId(id);
        return productDataService.addProductData(productData);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable int id) {
        productDataService.deleteProduct(id);
    }

    @GetMapping(path = "/customerProducts/{id}")
    public List<ProductData> displayProductByCustomer(@PathVariable int id) {
        return productDataService.getProductByCustomer(id);
    }

    @GetMapping(path = "/expired")
    public List<ProductData> getExpiredProducts() {

        return productDataService.getProductAboutToExpire();
    }
}
