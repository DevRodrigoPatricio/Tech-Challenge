package com.fiap.techChallenge.domain.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(UUID id);

    void delete(Product product);

    List<Product> list();

    List<Product> listAvaiables();

    List<Product> listByCategory(String category);

    List<Product> listAvaiablesByCategory(String category);

}
