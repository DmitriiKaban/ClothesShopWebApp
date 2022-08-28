package com.example.clothesshopwebapp.services;

import com.example.clothesshopwebapp.entity.Product;
import com.example.clothesshopwebapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getAll(int pageNumber, String sortField, String sortDirection,
                                String brandName, String colorName, String typeName, String sexName, String sizeName, String seasonName){

        Sort sort = sortField != null ? Sort.by(sortField) : Sort.by("price");
        sort = sortDirection.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, 12, sort);
        List<String> criteria = new ArrayList<>();
        criteria.add(brandName != null ? brandName : "");
        criteria.add(colorName != null ? colorName : "");
        criteria.add(typeName != null ? typeName : "");
        criteria.add(seasonName != null ? seasonName : "");
        criteria.add(sexName != null ? sexName : "");
        criteria.add(sizeName != null ? sizeName : "");
        return productRepository.findAll(pageable, brandName, colorName, typeName, sexName, sizeName, seasonName);
    }

    public Product save(Product product){
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }
    public void delete(Product product) {
        productRepository.delete(product);
    }
}
