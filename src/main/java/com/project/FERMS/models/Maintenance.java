package com.project.FERMS.models;

import com.project.FERMS.Security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Maintenance {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductData product;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User technician;
    @CreationTimestamp
    private LocalDate dateCreated;
    private int cost;
}
