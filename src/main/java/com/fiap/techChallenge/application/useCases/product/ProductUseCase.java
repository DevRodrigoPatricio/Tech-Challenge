package com.fiap.techChallenge.application.useCases.product;

import java.util.List;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.Category;
import com.fiap.techChallenge.domain.product.Product;

public interface ProductUseCase {
    
    Product save(Product product);

    Product findByName(String name);

    Product findById(UUID id);

    List<Category> listAvailableCategorys();

    List<Product> list();

    List<Product> listAvailables();

    List<Product> listByCategory(Category category);

    List<Product> listAvailablesByCategory(Category category);

    void delete(UUID id);

    void deleteByCategory(Category category);
}
