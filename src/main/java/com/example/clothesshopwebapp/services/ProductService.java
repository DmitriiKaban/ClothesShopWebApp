package com.example.clothesshopwebapp.services;

import com.example.clothesshopwebapp.entity.Product;
import com.example.clothesshopwebapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
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
