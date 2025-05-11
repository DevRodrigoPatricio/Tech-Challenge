package com.fiap.techChallenge.application.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.techChallenge.adapters.outbound.storage.product.ProductPort;
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

    public List<Product> list() {
        return port.list();
    }

    public List<Product> listAvaiables() {
        return port.listAvaiables();
    }

    public List<Product> listByCategory(UUID categoryId) {
        return port.listByCategory(categoryId);
    }

    public List<Product> listAvaiablesByCategory(UUID categoryId) {
        return port.listAvaiablesByCategory(categoryId);
    }

    public void delete(UUID id) {
        port.delete(id);
    }

    public void deleteByCategoryId(UUID categoryId) {
        port.deleteByCategoryId(categoryId);
    }

}
