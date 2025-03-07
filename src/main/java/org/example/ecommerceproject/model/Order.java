package org.example.ecommerceproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateCreated;

    private Integer quantity;

    private Date dateUpdated;

    private BigDecimal price;

    @ManyToOne()
    @JoinColumn(name = "shipping_id")
    private Shipping shipping;

    @ManyToOne()
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToMany (mappedBy = "order")
    private List<OrderItem> orderItems;


}
