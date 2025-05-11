package com.fiap.techChallenge.adapters.outbound.storage.category;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import com.fiap.techChallenge.domain.category.Category;

public interface CategoryPort {

    Category save(Category category);

    Optional<Category> findById(UUID id);

    List<Category> list();

    void delete(UUID id);
}
