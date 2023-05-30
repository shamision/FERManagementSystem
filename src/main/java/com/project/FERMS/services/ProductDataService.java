package com.project.FERMS.services;

import com.project.FERMS.models.Customer;
import com.project.FERMS.models.ProductData;
import com.project.FERMS.repositories.ProductDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    private final JavaMailSender mailSender;

    public ProductData addProductData(ProductData productData) {

        return productDataRepository.save(productData);
    }

    public List<ProductData> displayProductData() {

        return productDataRepository.findAll();
    }

    public ProductData displayProductById(Integer id) {
        return productDataRepository.findById(id).orElseThrow(()-> new IllegalStateException("Product doesn't exist!!"));
    }

    public void deleteProduct(Integer id) {
        productDataRepository.deleteById(id);
    }


    public List<ProductData> getProductByCustomer(int custId) {
        List<ProductData> products = productDataRepository.findAll();
        List<ProductData> customerProducts = new ArrayList<>();

        for (ProductData productData : products) {
            int customerId = productData.getCustomer().getId();
            if (custId == customerId ) {
                customerProducts.add(productData);
            }
        }
        return customerProducts;
    }

    public List<ProductData> getProductAboutToExpire() {
        List<ProductData> products = productDataRepository.findAll();
        List<ProductData> filteredProducts = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();

        for (ProductData productData : products) {
            long minutesDifference = ChronoUnit.DAYS.between(productData.getDateCreated(), currentDate);
            if (minutesDifference == 0) {
                filteredProducts.add(productData);

                Customer customer = productData.getCustomer();
                String email = customer.getEmail();
                String name = productData.getProductName();
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setFrom("shami0sion@gmail.com");
                simpleMailMessage.setTo(email);
                simpleMailMessage.setSubject("Fire Extinguisher Expired");
                simpleMailMessage.setText("The fire extinguisher named: "+name+" is expired. It needs to be renewed");

                this.mailSender.send(simpleMailMessage);
            }
        }
        return filteredProducts;

    }
}
