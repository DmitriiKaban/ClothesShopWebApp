package com.example.clothesshopwebapp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "tbl_bankcard")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private Integer code;

    @Min(value=1, message="Month must be equal or greater than 1")
    @Max(value=12, message="Month must be equal or less than 12")
    private Integer expiryMonth;

    @Min(value=22, message="Year must be equal or greater than 22")
    @Max(value=50, message="Year must be equal or less than 50")
    private Integer expiryYear;

    private Integer cvv;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
