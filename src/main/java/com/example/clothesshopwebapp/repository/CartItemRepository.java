package com.example.clothesshopwebapp.repository;

import com.example.clothesshopwebapp.entity.Account;
import com.example.clothesshopwebapp.entity.CartItem;
import com.example.clothesshopwebapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByAccount(Account account);
    CartItem findByProduct(Product product);

    CartItem findByAccountAndProduct(Account account, Product product);

    @Query("UPDATE CartItem c SET c.quantity=?1 WHERE c.product.id = ?2 " +
            "AND c.account.id = ?3")
    @Modifying
    void updateQuantity(Integer quantity, Long productId, Long accountId);

    @Query("DELETE FROM CartItem c WHERE c.account.id = ?1 AND c.product.id = ?2")
    @Modifying
    void deleteByAccountAndProduct(Long accountId, Long productId);
}
