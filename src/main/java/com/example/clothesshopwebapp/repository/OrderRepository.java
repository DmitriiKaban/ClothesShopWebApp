package com.example.clothesshopwebapp.repository;

import com.example.clothesshopwebapp.entity.Account;
import com.example.clothesshopwebapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByAccount(Account account);
}
