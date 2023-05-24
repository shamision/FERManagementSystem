package com.project.FERMS.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String email;
    private String phone;
//    @OneToMany(targetEntity = ProductData.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "customer_id", referencedColumnName = "id")
//    @OneToMany(mappedBy = "customer")
//    private List<ProductData> productData;
}
