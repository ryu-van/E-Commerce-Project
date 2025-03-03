package org.example.ecommerceproject.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
@Entity
@Table(name = "promotions")
public class Promotion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double discount;

    private String code;

    private Date startDate;

    private Date endDate;

    private String status;

    @ManyToMany(mappedBy = "promotions")
    private Set<Product> products;


}
