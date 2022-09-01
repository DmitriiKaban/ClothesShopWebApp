package com.example.clothesshopwebapp.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }
    @GetMapping("/login/error")
    public ModelAndView getLogin(@Param("error") String error){
        ModelAndView mav = new ModelAndView("/login");
        mav.addObject("error", "Credentials are wrong!");
        return mav;
    }
}
