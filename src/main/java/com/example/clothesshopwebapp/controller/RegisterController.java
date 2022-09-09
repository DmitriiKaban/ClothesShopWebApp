package com.example.clothesshopwebapp.controller;

import com.example.clothesshopwebapp.entity.Account;
import com.example.clothesshopwebapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        Account account = new Account();
        model.addAttribute("account", account);
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerNewUser(@ModelAttribute Account account){
        ModelAndView mav = new ModelAndView("login");
        if(accountService.findOneByEmail(account.getEmail()).isPresent()){
            mav = new ModelAndView("register");
            mav.addObject("user_exists", true);
            return mav;
        }
        accountService.save(account);

        return mav;
    }
}
