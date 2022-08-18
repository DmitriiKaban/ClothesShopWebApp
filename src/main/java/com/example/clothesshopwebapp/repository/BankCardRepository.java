package com.example.clothesshopwebapp.repository;

import com.example.clothesshopwebapp.entity.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCardRepository extends JpaRepository<BankCard, Long> {
}
