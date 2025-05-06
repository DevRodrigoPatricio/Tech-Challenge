package com.fiap.techChallenge.adapters.outbound.repositories.product;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.techChallenge.adapters.outbound.entities.ProductEntity;

public interface JpaProductRepository extends JpaRepository<ProductEntity, UUID> {
}
