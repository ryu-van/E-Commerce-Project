package org.example.ecommerceproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
@Entity
@Table(name = "shippings")
public class Shipping {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameType;

    private String description;

    private Double price;

    private String deliveryTime;

    private String trackingNumber;

    private String status;

    @OneToMany(mappedBy = "shipping")
    private List<Order> orders;
}
