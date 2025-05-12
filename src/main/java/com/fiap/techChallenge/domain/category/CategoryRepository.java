package com.fiap.techChallenge.domain.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {

    Category save(Category category);

    List<Category> saveAll(List<Category> categoryList);

    Optional<Category> findById(UUID id);

    int findLastDisplayOrder();

    Category validate(UUID id);

    Boolean existsById(UUID id);

    Optional<Category> findByName(String name);

    List<Category> list();

    List<Category> listByDisplayOrder(int displayOrder);

    List<Category> findByDisplayOrderRange(int start, int end);

    void delete(UUID id);
}
