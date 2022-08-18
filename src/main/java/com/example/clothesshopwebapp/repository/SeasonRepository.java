package com.example.clothesshopwebapp.repository;

import com.example.clothesshopwebapp.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
}
