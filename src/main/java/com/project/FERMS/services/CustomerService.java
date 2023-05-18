package com.project.FERMS.services;

import com.project.FERMS.models.Customer;
import com.project.FERMS.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    public void addNewCustomer(Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(customer.getEmail());
        if (customerOptional.isPresent()) {
            throw new IllegalStateException("The email is taken!!");
        }
        customerRepository.save(customer);
    }

    public List<Customer> displayCustomers() {
        return customerRepository.findAll();
    }

    public Customer findById(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateCustomer(Integer id, String name, String email, String phone) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalStateException("Customer with id"+ id + "does not exist!"));

        if (name != null && name.length() > 0 && !Objects.equals(customer.getName(), name)) {
            customer.setName(name);
        }

        if (email != null && email.length() >  0 && !Objects.equals(customer.getEmail(),email)) {
            Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(email);
            if (customerOptional.isPresent()) {
                throw new IllegalStateException("This email is taken!");
            }
            customer.setEmail(email);
        }

        if (phone != null && phone.length() > 0 && !Objects.equals(customer.getPhone(), phone)) {
            customer.setPhone(phone);
        }
    }

    public void deleteCustomer(Integer id) {
        boolean exists = customerRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Student with id"+ id + "does not exists!");
        }
        customerRepository.deleteById(id);
    }
}
