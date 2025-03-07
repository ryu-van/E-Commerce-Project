package org.example.ecommerceproject.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentName;

    private String paymentStatus;

    private BigDecimal paymentAmount;

    private Date paymentDate;

    private Long paymentResponseCode;

    private String paymentResponseMessage;

    private Long paymentBankCode;

    private String paymentBankMessage;

    private String paymentBankName;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "payment")
    private List<Order> orders;

    @OneToMany(mappedBy = "payment")
    private List<Transaction> transactions;


}
