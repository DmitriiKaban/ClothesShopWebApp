package com.example.clothesshopwebapp.controller;

import com.example.clothesshopwebapp.entity.Account;
import com.example.clothesshopwebapp.entity.CartItem;
import com.example.clothesshopwebapp.services.AccountService;
import com.example.clothesshopwebapp.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/cart")
    public String showShoppingCart(Model model,
                                   @AuthenticationPrincipal Authentication authentication){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        Optional<Account> account = accountService.findOneByEmail(username);

        List<CartItem> cartItems = cartService.listCartItems(account.get());

        if(username != null){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Account acc = accountService.findOneByEmail(auth.getName()).get();
            model.addAttribute("user_name", acc.getFirstName() + ' ' + acc.getLastName());
        }

        model.addAttribute("cartItems", cartItems);
        return "shopping_cart";
    }

}
