package com.example.clothesshopwebapp.controller;

import com.example.clothesshopwebapp.entity.Product;
import com.example.clothesshopwebapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/details-product")
    public ModelAndView productDetails(@Param("id") Long id){//@PathVariable Long id){
        System.out.println(id);
        //Long i = Long.parseLong(id);
        ModelAndView mav = new ModelAndView("details-product");
        Optional<Product> product = productRepo.findById(id);
        mav.addObject("product", product.get());

        return mav;
    }
}
