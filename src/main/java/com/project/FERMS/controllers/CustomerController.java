package com.project.FERMS.controllers;

import com.project.FERMS.models.Customer;
import com.project.FERMS.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/customer")
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    @PostMapping(path = "/register")
    public void registerNewCustomer (@RequestBody Customer customer) {

        customerService.addNewCustomer(customer);
    }

    @GetMapping
    public List<Customer> displayCustomers() {
        return customerService.displayCustomers();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        Customer customer = customerService.findById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(path = "/update/{id}")
    public void updateCustomer(@PathVariable("id") Integer id, @RequestParam(required = false)String name, @RequestParam(required = false)String email,@RequestParam(required = false)String phone) {
        customerService.updateCustomer(id, name, email, phone);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteStudent(@PathVariable("id") Integer id) {
        customerService.deleteCustomer(id);
    }
}
