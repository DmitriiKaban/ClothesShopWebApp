package com.example.clothesshopwebapp.config;

import com.example.clothesshopwebapp.entity.Account;
import com.example.clothesshopwebapp.entity.Authority;
import com.example.clothesshopwebapp.entity.Product;
import com.example.clothesshopwebapp.repository.AuthorityRepository;
import com.example.clothesshopwebapp.services.AccountService;
import com.example.clothesshopwebapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private ProductService productService;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public void run(String ... args) throws Exception{
        List<Product> list = productService.getAll();

        if(list.isEmpty()){
            // list.add() // add a few products
            Authority user = new Authority();
            user.setName("ROLE_USER");
            authorityRepository.save(user);

            Authority admin = new Authority();
            admin.setName("ROLE_ADMIN");
            authorityRepository.save(admin);

            Account acc1 = new Account();
            Account acc2 = new Account();
            acc1.setFirstName("user");
            acc1.setLastName("user");
            acc1.setEmail("user@gmail.com");
            acc1.setPassword("password");
            Set<Authority> authorities1 = new HashSet<>();
            authorityRepository.findById("ROLE_USER").ifPresent(authorities1::add);
            acc1.setAuthorities(authorities1);


            acc2.setFirstName("admin");
            acc2.setLastName("admin");
            acc2.setEmail("admin@gmail.com");
            acc2.setPassword("password");
            Set<Authority> authorities2 = new HashSet<>();
            // has both user and admin roles
            authorityRepository.findById("ROLE_USER").ifPresent(authorities2::add);
            authorityRepository.findById("ROLE_ADMIN").ifPresent(authorities2::add);
            acc2.setAuthorities(authorities2);

            accountService.save(acc1);
            accountService.save(acc2);
        }
    }

}
