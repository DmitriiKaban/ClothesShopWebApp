package com.example.clothesshopwebapp.repository;

import com.example.clothesshopwebapp.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{// JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.brand.name LIKE %?1%" +
            " AND p.color.name LIKE %?2%" +
            " AND p.type.name LIKE %?3%" +
            " AND p.sex.name LIKE %?4%" +
            " AND p.size.name LIKE %?5%" +
            " AND p.season.name LIKE %?6%")
    Page<Product> findAll(Pageable pageable, String brandName, String colorName, String typeName, String sexName, String sizeName, String seasonName);
}
