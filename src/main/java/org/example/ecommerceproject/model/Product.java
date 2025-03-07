package org.example.ecommerceproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    private String seasonName;

    private String status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_promotions",
            joinColumns = @JoinColumn(name = "product_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "promotion_id",referencedColumnName = "id")
    )
    private Set<Promotion> promotions;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product")
    private List<Cart> carts;

    @OneToMany(mappedBy = "product")
    private List<ProductFile> productFiles;

    @OneToMany(mappedBy = "product")
    private List<ProductVariants> productVariants;





}
