package org.example.ecommerceproject.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "order_status")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    @OneToMany(mappedBy = "orderStatus")
    private List<Order> orders;

}
