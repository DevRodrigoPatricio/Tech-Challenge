package com.fiap.techChallenge.domain.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.ProductStatus;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(UUID id);

    Optional<Product> findByName(String name);

    void delete(Product product);

    List<Product> list();

    List<Product> listByCategory(String category);

    List<Product> listByStatusAndCategory(ProductStatus status, String category);

    List<Product> listByStatus(ProductStatus status);

}
