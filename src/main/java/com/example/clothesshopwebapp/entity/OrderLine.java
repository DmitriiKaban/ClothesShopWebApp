package com.example.clothesshopwebapp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_order_line")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "product_id")
    @OneToOne
    private Product product;


    @JoinColumn(name = "costumer_id")
    @ManyToOne
    private Customer customer;


    @JoinColumn(name = "order_id")
    @ManyToOne
    private Order order;

    private Double price;

    private Integer quantity;
}
