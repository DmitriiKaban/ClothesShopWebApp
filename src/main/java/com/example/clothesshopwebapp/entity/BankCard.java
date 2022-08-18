package com.example.clothesshopwebapp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_bankcard")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer code;

    private Integer cvv;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
