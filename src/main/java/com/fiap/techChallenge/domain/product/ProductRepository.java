package com.fiap.techChallenge.domain.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product produto);

    Optional<Product> findById(UUID id);

    Product update(Product produto);

    void delete(Product produto);

    List<Product> list();

    List<Product> listAvaiables();

    List<Product> listByCategory(String categoria);

    List<Product> listAvaiablesByCategory(String categoria);

}
