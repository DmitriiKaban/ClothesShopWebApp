package com.example.clothesshopwebapp.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table(name = "tbl_customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotBlank(message = "First name should not be null")
    private String firstName;

    private String lastName;

    @Email
    private String email;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
