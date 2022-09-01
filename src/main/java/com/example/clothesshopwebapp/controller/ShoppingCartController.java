package com.example.clothesshopwebapp.controller;

import com.example.clothesshopwebapp.entity.*;
import com.example.clothesshopwebapp.repository.*;
import com.example.clothesshopwebapp.services.AccountService;
import com.example.clothesshopwebapp.services.MailService;
import com.example.clothesshopwebapp.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.clothesshopwebapp.controller.ProductController.getUserDetail;

@Controller
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private BankCardRepository bankCardRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderLineRepository orderLineRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private MailService mailService;

    @PostMapping("/cart/add/{id}/{qty}")
    public String addProductToCart(@PathVariable("id") Long id, @PathVariable("qty") Integer qty,
                                   @AuthenticationPrincipal Authentication authentication){
        System.out.println("Add product to cart! Id = " + id + ", " + " qty = " + qty);
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

        String username = loggedInUser.getName();
        Account account = accountService.findOneByEmail(username).get();

        Integer addedQuantity = shoppingCartService.addProduct(id, qty, account);


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
    @GetMapping("/checkout")
    public ModelAndView checkout(){
        ModelAndView mav = new ModelAndView("checkout_final_form");
        BankCard bankCard = new BankCard();
        Address address = new Address();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            List<Country> countries = countryRepository.findAll();
            mav.addObject("countries", countries);
            Account account = accountService.findOneByEmail(auth.getName()).get();
        }

        List<Country> countries = countryRepository.findAll();
        mav.addObject("countries", countries);

        mav.addObject("bankCard", bankCard);
        mav.addObject("address", address);
        return mav;
    }

    @PostMapping("/checkout")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public String checkout(@ModelAttribute(value = "bankCard") BankCard bankCard, @ModelAttribute(value = "address") Address address, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account account = accountService.findOneByEmail(auth.getName()).get();
        bankCard.setAccount(account);
        System.out.println(bankCard);
        System.out.println(account);
        bankCardRepository.save(bankCard);

        Order order = new Order();
        order.setDate(new Date());
        orderRepository.save(order);
        addressRepository.save(address);
        order.setAddress(address);
        List<CartItem> listOfItems = shoppingCartService.listCartItems(account);
        String stringListOfProducts = "";
        stringListOfProducts += "The list of products you ordered:";
        double totalPrice = 0D;
        for (var item :
                listOfItems) {
            if(item.getProduct().getQuantity() - item.getQuantity() >= 0){
                item.getProduct().setQuantity(item.getProduct().getQuantity() - item.getQuantity());
                productRepo.save(item.getProduct());
            }
            else {
                item.setQuantity(item.getProduct().getQuantity());
                item.getProduct().setQuantity(0);
                productRepo.save(item.getProduct());
            }

            OrderLine orderLine = new OrderLine();
            orderLine.setOrder(order);
            orderLine.setProduct(item.getProduct());
            orderLine.setAccount(account);
            orderLine.setQuantity(item.getQuantity());
            orderLine.setPrice(item.getProduct().getPrice());
            orderLineRepository.save(orderLine);

            stringListOfProducts += "<br>" + item.getProduct().getColor().getName() + " " + item.getProduct().getType().getName() +
                    " by " + item.getProduct().getBrand().getName() + " for $" + item.getProduct().getPrice() + " - " +
                    item.getQuantity() + " qty";
            totalPrice += item.getQuantity() * item.getProduct().getPrice();

            shoppingCartService.removeProduct(item.getProduct().getId(), account);
        }

        stringListOfProducts += "<br>Total price is $" + totalPrice;
        stringListOfProducts += "<br>Your order will be delivered to " + address.getCountry().getName() + ", str." + address.getStreet() + " bl." + address.getHouse() + ", ap." + address.getApartment();
        try {
            mailService.send("bertha.shop.company@gmail.com", account.getEmail(), stringListOfProducts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!auth.getName().equals("anonymousUser")) {
            account = accountService.findOneByEmail(auth.getName()).get();
            model.addAttribute("user_name", account.getFirstName() + ' ' + account.getLastName());
        }
        return showShoppingCart(model, auth);
    }

}
