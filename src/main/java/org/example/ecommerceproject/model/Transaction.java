package org.example.ecommerceproject.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
@Entity
@Table(name = "transactions")
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal totalMoney;

    private String message;

    private Date dateCreate;

    private Date dateUpdate;

    private String status;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;









}
