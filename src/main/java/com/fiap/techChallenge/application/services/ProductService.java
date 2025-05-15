package com.fiap.techChallenge.application.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.techChallenge.adapters.outbound.storage.product.ProductPort;
import com.fiap.techChallenge.domain.enums.Category;
import com.fiap.techChallenge.domain.product.Product;

@Service
public class ProductService {

    private final ProductPort port;

    public ProductService(ProductPort port) {
        this.port = port;
    }

    public Product save(Product product) {
        return port.save(product);
    }

    public Optional<Product> findByName(String name) {
        return port.findByName(name);
    }

    public Optional<Product> findById(UUID id) {
        return port.findById(id);
    }

    public List<Category> listAvaiableCategorys() {
        return port.listAvaiableCategorys();
    }

    public List<Product> list() {
        return port.list();
    }

    public List<Product> listAvaiables() {
        return port.listAvaiables();
    }

    public List<Product> listByCategory(Category category) {
        return port.listByCategory(category);
    }

    public List<Product> listAvaiablesByCategory(Category category) {
        return port.listAvaiablesByCategory(category);
    }

    public void delete(UUID id) {
        port.delete(id);
    }

    public void deleteByCategory(Category category) {
        port.deleteByCategory(category);
    }

}
