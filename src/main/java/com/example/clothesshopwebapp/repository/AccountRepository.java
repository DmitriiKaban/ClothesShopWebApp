package com.example.clothesshopwebapp.repository;

import com.example.clothesshopwebapp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findOneByEmailIgnoreCase(String email);
}
