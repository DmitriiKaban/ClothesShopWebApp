package com.example.clothesshopwebapp.repository;

import com.example.clothesshopwebapp.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findTypeByNameContainingIgnoreCase(String name);
}
