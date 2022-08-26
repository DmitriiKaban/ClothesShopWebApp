package com.example.clothesshopwebapp;

import com.example.clothesshopwebapp.entity.Account;
import com.example.clothesshopwebapp.entity.CartItem;
import com.example.clothesshopwebapp.entity.Product;
import com.example.clothesshopwebapp.repository.CartItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ShoppingCartTest {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testAddOneCartItem(){
        Product product = entityManager.find(Product.class, 12L);
        Account account = entityManager.find(Account.class, 5L);

        CartItem  cartItem = new CartItem();
        cartItem.setAccount(account);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        assertTrue(savedCartItem.getId() > 0);
    }

    @Test
    public void testGetItemsByCustomer(){
        Account account = new Account();
        account.setId(22L);

        List<CartItem> cartItems = cartItemRepository.findByAccount(account);
        assertEquals(3, cartItems.size());
    }
}
