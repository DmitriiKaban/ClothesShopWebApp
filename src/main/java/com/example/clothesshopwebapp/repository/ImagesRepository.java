package com.example.clothesshopwebapp.repository;

import com.example.clothesshopwebapp.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}
