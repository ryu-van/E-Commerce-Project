package org.example.ecommerceproject.model;


import jakarta.persistence.*;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;





}
