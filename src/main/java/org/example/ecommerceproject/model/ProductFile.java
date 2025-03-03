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
@Table(name = "product_files")
public class ProductFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String videoUrl;

    private String imageUrl;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;
}
