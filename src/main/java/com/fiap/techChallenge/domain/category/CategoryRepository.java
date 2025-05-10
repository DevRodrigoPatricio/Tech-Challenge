package com.fiap.techChallenge.domain.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fiap.techChallenge.adapters.outbound.entities.CategoryEntity;

public interface CategoryRepository {

    Category save(Category category);

    Optional<Category> findById(UUID id);

    CategoryEntity validate(UUID id);

    Optional<Category> findByName(String name);

    List<Category> list();

    void delete(UUID id);
}
