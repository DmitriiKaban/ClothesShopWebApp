package com.example.clothesshopwebapp.repository;

import com.example.clothesshopwebapp.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.brand.name LIKE %?1%")
    Page<Product> findByBrand(String name, Pageable pageable);
}
