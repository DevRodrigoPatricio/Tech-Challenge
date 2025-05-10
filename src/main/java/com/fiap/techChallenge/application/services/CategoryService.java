package com.fiap.techChallenge.application.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.techChallenge.adapters.outbound.storage.category.CategoryPort;
import com.fiap.techChallenge.domain.category.Category;
import com.fiap.techChallenge.domain.category.CategoryRequest;

@Service
public class CategoryService {

    private final CategoryPort port;

    public CategoryService(CategoryPort port) {
        this.port = port;
    }

    public Category save(CategoryRequest request) {
        return port.save(request);
    }

    public Optional<Category> findById(UUID id) {
        return port.findById(id);
    }

    public List<Category> list() {
        return port.list();
    }

    public void delete(UUID id) {
        port.delete(id);
    }

}
