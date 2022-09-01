package com.example.clothesshopwebapp.services;

import com.example.clothesshopwebapp.entity.Account;
import com.example.clothesshopwebapp.entity.CartItem;
import com.example.clothesshopwebapp.entity.Product;
import com.example.clothesshopwebapp.repository.CartItemRepository;
import com.example.clothesshopwebapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ShoppingCartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CartItem> listCartItems(Account account){
        return cartItemRepository.findByAccount(account);
    }
    public CartItem findByProduct(Product product){
        return cartItemRepository.findByProduct(product);
    }

    public Integer addProduct(Long productId, Integer quantity, Account account){
        Integer addedQuantity = quantity;

        Product product = productRepository.findById(productId).get();

        CartItem cartItem = cartItemRepository.findByAccountAndProduct(account, product);

        if(cartItem != null){
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
        }else{
            cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setAccount(account);
            cartItem.setProduct(product);
        }

        cartItemRepository.save(cartItem);

        return addedQuantity;
    }

    public Double updateQuantity(Long productId, Integer quantity, Account account){
        cartItemRepository.updateQuantity(quantity, productId, account.getId());

        Product product = productRepository.findById(productId).get();
        double subtotal = product.getPrice() * quantity;
        return subtotal;
    }

    public void removeProduct(Long productId, Account account){
        cartItemRepository.deleteByAccountAndProduct(account.getId(), productId);
    }


    public boolean findByProductAndAccount(Product product, Account account) {
        return cartItemRepository.findByAccountAndProduct(account, product) != null;
    }
}
