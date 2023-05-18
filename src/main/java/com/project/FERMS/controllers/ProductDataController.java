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
    public void registerNewProductData(@RequestBody ProductData productData){
        productDataService.addProductData(productData);
    }

    @GetMapping(path = "/display")
    public List<ProductData> displayProductData() {

        return productDataService.displayProductData();
    }

    @GetMapping(path = "/expired")
    public List<ProductData> getExpiredProducts() {
        return productDataService.getProductAboutToExpire();
    }
}
