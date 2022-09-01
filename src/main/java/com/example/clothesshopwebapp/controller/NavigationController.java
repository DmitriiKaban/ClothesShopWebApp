package com.example.clothesshopwebapp.controller;

import com.example.clothesshopwebapp.entity.Account;
import com.example.clothesshopwebapp.services.AccountService;
import com.example.clothesshopwebapp.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.NoSuchElementException;

import static com.example.clothesshopwebapp.controller.ProductController.getUserDetail;

@Controller
public class NavigationController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/about")
    public ModelAndView aboutCompany() {
        ModelAndView mav = new ModelAndView("about");
        return getUserDetail(mav, accountService, shoppingCartService);
    }

    @GetMapping("/delivery")
    public ModelAndView deliveryInfo() {
        ModelAndView mav = new ModelAndView("deliveryInfo");
        return getUserDetail(mav, accountService, shoppingCartService);
    }
    @GetMapping("/contact")
    public ModelAndView contact() {
        ModelAndView mav = new ModelAndView("contactInfo");
        return getUserDetail(mav, accountService, shoppingCartService);
    }


    @ExceptionHandler(value = TemplateInputException.class)
    public ModelAndView handleException() {
        ModelAndView mav = new ModelAndView("404");
        mav.addObject("error_message", "Template Input Exception caused!");

        return getUserDetail(mav, accountService, shoppingCartService);
    }
}
