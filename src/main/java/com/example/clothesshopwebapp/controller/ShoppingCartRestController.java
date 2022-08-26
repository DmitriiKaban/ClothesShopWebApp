package com.example.clothesshopwebapp.controller;

import com.example.clothesshopwebapp.entity.Account;
import com.example.clothesshopwebapp.services.AccountService;
import com.example.clothesshopwebapp.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ShoppingCartRestController {

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/cart/add/{id}/{qty}")
    public String addProductToCart(@PathVariable("id") Long id, @PathVariable("qty") Integer qty,
                                         @AuthenticationPrincipal Authentication authentication){
        System.out.println("Add product to cart!" + id + " " + " qty = " + qty);
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

//        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
//            return "You must login to add product to shopping cart";
//        }
        String username = loggedInUser.getName();
        Account account = accountService.findOneByEmail(username).get();

        Integer addedQuantity = shoppingCartService.addProduct(id, qty, account);

        System.out.println("Added quantity " + addedQuantity);

        return addedQuantity + " item(s) of the product were added to shopping cart.";
    }

    @PostMapping("/cart/update/{id}/{qty}")
    @ResponseBody
    public String updateQuantity(@PathVariable("id") Long id, @PathVariable("qty") Integer qty, @AuthenticationPrincipal Authentication authentication){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

        String username = loggedInUser.getName();
        Account account = accountService.findOneByEmail(username).get();

        Double subtotal = shoppingCartService.updateQuantity(id, qty, account);

        System.out.println(subtotal);

        return String.valueOf(subtotal);
    }

    @PostMapping("/cart/remove/{id}")
    @ResponseBody
    public String removeProductFromCart(@PathVariable("id") Long id, @AuthenticationPrincipal Authentication authentication){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

        String username = loggedInUser.getName();
        Account account = accountService.findOneByEmail(username).get();

        shoppingCartService.removeProduct(id, account);
        return "The product was removed from your shopping cart";
    }
}
