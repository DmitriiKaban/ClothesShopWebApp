package com.example.clothesshopwebapp.repository;

import com.example.clothesshopwebapp.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Long> {
    Color findColorByNameContainingIgnoreCase(String name);
}
