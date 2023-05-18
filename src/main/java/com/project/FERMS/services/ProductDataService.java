package com.project.FERMS.services;

import com.project.FERMS.models.ProductData;
import com.project.FERMS.repositories.ProductDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDataService {
    @Autowired
    private final ProductDataRepository productDataRepository;

    public void addProductData(ProductData productData) {
        productDataRepository.save(productData);
    }

    public List<ProductData> displayProductData() {

        return productDataRepository.findAll();
    }

    public List<ProductData> getProductAboutToExpire() {
        List<ProductData> products = productDataRepository.findAll();
        List<ProductData> filteredProducts = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();

        for (ProductData productData : products) {
            long minutesDifference = ChronoUnit.DAYS.between(productData.getDateCreated(), currentDate);
            if (minutesDifference == 0) {
                filteredProducts.add(productData);
            }
        }
        return filteredProducts;

    }
}
