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
@Table(name = "product_variants")
public class ProductVariants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String colorCode;

    private String size;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_variants_id")
    private Product product;
}
