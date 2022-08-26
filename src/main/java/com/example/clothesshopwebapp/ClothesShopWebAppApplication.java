package com.example.clothesshopwebapp;

import com.example.clothesshopwebapp.controller.ProductController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ClothesShopWebAppApplication {

    public static void main(String[] args) {

        new File(ProductController.uploadDirectory).mkdir();

        SpringApplication.run(ClothesShopWebAppApplication.class, args);
    }

}
