package com.fiap.techChallenge.adapters.outbound.storage.category;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import com.fiap.techChallenge.domain.category.Category;
import com.fiap.techChallenge.domain.category.CategoryRequest;

public interface CategoryPort {

    Category save(CategoryRequest request);

    Optional<Category> findById(UUID id);

    List<Category> list();

    void delete(UUID id);
}
