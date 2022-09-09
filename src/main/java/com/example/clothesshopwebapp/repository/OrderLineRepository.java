package com.example.clothesshopwebapp.repository;

import com.example.clothesshopwebapp.entity.Account;
import com.example.clothesshopwebapp.entity.CartItem;
import com.example.clothesshopwebapp.entity.Order;
import com.example.clothesshopwebapp.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

    List<OrderLine> findByOrder(Order order);
}
