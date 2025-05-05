package com.fiap.techChallenge.adapters.outbound.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.techChallenge.adapters.outbound.entities.ProductEntity;

public interface SpringProductRepository extends JpaRepository<ProductEntity, UUID> {
}
