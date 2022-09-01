package com.example.clothesshopwebapp.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import com.example.clothesshopwebapp.entity.Account;
import com.example.clothesshopwebapp.services.AccountService;
import com.example.clothesshopwebapp.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomErrorController  implements ErrorController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        System.out.println("Status");
        ModelAndView mav = new ModelAndView("404");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                mav.addObject("error_message", "Not Found 404 error caused!");
                getUserDetail(mav, auth);
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                mav.addObject("error_message", "Forbidden 403 error caused!");
                getUserDetail(mav, auth);

            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                mav.addObject("error_message", "Internal Server error caused!");
                getUserDetail(mav, auth);

            }
        }else{
            mav.addObject("error_message", "Error with unknown status caused!");
            getUserDetail(mav, auth);
        }

        return mav;
    }

    private void getUserDetail(ModelAndView mav, Authentication auth) {
        if (!auth.getName().equals("anonymousUser")) {
            Account account = accountService.findOneByEmail(auth.getName()).get();
            mav.addObject("user_name", account.getFirstName() + ' ' + account.getLastName());
            int numItems = shoppingCartService.listCartItems(accountService.findOneByEmail(auth.getName()).get()).size();
            mav.addObject("cart_items_num", numItems);
        }
    }

}