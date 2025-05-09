package com.fiap.techChallenge.adapters.outbound.storage.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fiap.techChallenge.domain.product.Product;
import com.fiap.techChallenge.domain.product.ProductRequest;

public interface ProductPort {

    Product save(ProductRequest request);

    Optional<Product> findById(UUID id);

    Optional<Product> findByName(String name);

    List<Product> list();

    List<Product> listAvaiables();

}
