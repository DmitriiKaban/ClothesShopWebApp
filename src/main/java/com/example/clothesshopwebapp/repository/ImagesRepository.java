package com.example.clothesshopwebapp.repository;

import com.example.clothesshopwebapp.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Image, Long> {
}
