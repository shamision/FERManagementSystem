package com.project.FERMS.services;

import com.project.FERMS.models.Customer;
import com.project.FERMS.models.Equipment;
import com.project.FERMS.repositories.EquipmentRepository;
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
public class EquipmentService {
    @Autowired
    private final EquipmentRepository equipmentRepository;

    private final JavaMailSender mailSender;

    public Equipment addEquipment(Equipment equipment) {

        return equipmentRepository.save(equipment);
    }

    public List<Equipment> displayEquipment() {

        return equipmentRepository.findAll();
    }

    public Equipment displayEquipmentById(Integer id) {
        return equipmentRepository.findById(id).orElseThrow(()-> new IllegalStateException("Equipment doesn't exist!!"));
    }

    public void deleteEquipment(Integer id) {
        equipmentRepository.deleteById(id);
    }


    public List<Equipment> getEquipmentByCustomer(int custId) {
        List<Equipment> products = equipmentRepository.findAll();
        List<Equipment> customerProducts = new ArrayList<>();

        for (Equipment productData : products) {
            int customerId = productData.getCustomer().getId();
            if (custId == customerId ) {
                customerProducts.add(productData);
            }
        }
        return customerProducts;
    }

    public List<Equipment> getEquipmentAboutToExpire() {
        List<Equipment> equipments = equipmentRepository.findAll();
        List<Equipment> filteredProducts = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();

        for (Equipment equipment : equipments) {
            long minutesDifference = ChronoUnit.DAYS.between(equipment.getDateCreated(), currentDate);
            if (minutesDifference == 0) {
                filteredProducts.add(equipment);

                Customer customer = equipment.getCustomer();
                String email = customer.getEmail();
                String name = equipment.getProductName();
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
