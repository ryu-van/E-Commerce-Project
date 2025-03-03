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
@Table(name = "payment_method")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String methodName;

    private String methodDescription;

    @OneToMany(mappedBy = "paymentMethod")
    private List<Payment> payments;

}
