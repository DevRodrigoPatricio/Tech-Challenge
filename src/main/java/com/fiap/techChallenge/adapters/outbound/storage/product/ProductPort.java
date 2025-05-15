package com.fiap.techChallenge.adapters.outbound.storage.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.Category;
import com.fiap.techChallenge.domain.product.Product;

public interface ProductPort {

    Product save(Product product);

    Optional<Product> findById(UUID id);

    Optional<Product> findByName(String name);

    List<Category> listAvaiableCategorys();

    List<Product> list();

    List<Product> listAvaiables();

    List<Product> listByCategory(Category category);

    List<Product> listAvaiablesByCategory(Category category);

    void delete(UUID id);

    void deleteByCategory(Category category);

}
