package com.fiap.techChallenge.domain.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.ProductStatus;
import com.fiap.techChallenge.domain.enums.Category;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(UUID id);

    Optional<Product> findByName(String name);

    List<Category> listAvaiableCategorys();

    List<Product> list();

    List<Product> listByCategory(Category category);

    List<Product> listByStatusAndCategory(ProductStatus status, Category category);

    List<Product> listByStatus(ProductStatus status);

    void delete(UUID id);

    void deleteByCategory(Category category);
}
