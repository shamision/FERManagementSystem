package com.project.FERMS.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Equipment {

    @Id
    @GeneratedValue
    private int id;
    private String productName;
    private int qty;
    private int price;
    @CreationTimestamp
    private LocalDate dateCreated;
    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;
}
