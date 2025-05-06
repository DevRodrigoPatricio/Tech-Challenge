package com.fiap.techChallenge.application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fiap.techChallenge.domain.product.Product;
import com.fiap.techChallenge.domain.product.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public List<Product> list() {
        return repository.list();
    }

    public List<Product> listAvaiables() {
        return repository.listAvaiables();
    }

    public List<Product> listByCategory(String category) {
        return repository.listByCategory(category);
    }

    public List<Product> listAvaiablesByCategory(String category) {
        return repository.listAvaiablesByCategory(category);
    }
}
