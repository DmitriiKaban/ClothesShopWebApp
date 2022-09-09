package com.example.clothesshopwebapp.repository;

import com.example.clothesshopwebapp.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findBrandByNameContainingIgnoreCase(String name);
}
