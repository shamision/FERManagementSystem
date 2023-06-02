package com.project.FERMS.controllers;

import com.project.FERMS.models.Equipment;
import com.project.FERMS.services.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/equipment")
public class EquipmentController {

    @Autowired
    private final EquipmentService productDataService;

    @PostMapping(path = "/register")
    public Equipment registerNewProductData(@RequestBody Equipment equipment){
        return productDataService.addEquipment(equipment);
    }

    @GetMapping
    public List<Equipment> displayProductData() {
        return productDataService.displayEquipment();
    }

    @GetMapping(path = "/display/{id}")
    public Equipment displayProductById(@PathVariable int id) {
        return productDataService.displayEquipmentById(id);
    }

    @PutMapping(path = "/update/{id}")
    public Equipment updateProduct(@PathVariable int id, @RequestBody Equipment productData) {
//        productData.setId(id);
        return productDataService.addEquipment(productData);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable int id) {
        productDataService.deleteEquipment(id);
    }

    @GetMapping(path = "/customerEquipments/{id}")
    public List<Equipment> displayProductByCustomer(@PathVariable int id) {
        return productDataService.getEquipmentByCustomer(id);
    }

    @GetMapping(path = "/expired")
    public List<Equipment> getExpiredProducts() {

        return productDataService.getEquipmentAboutToExpire();
    }
}
