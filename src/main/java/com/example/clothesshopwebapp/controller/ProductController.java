package com.example.clothesshopwebapp.controller;

import com.example.clothesshopwebapp.entity.Product;
import com.example.clothesshopwebapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @GetMapping({"/", "/products"})
    public ModelAndView showProducts(){
        ModelAndView mav = new ModelAndView("list-products");
        List<Product> list = productRepo.findAll();
        mav.addObject("products", list);

        return mav;
    }
}
