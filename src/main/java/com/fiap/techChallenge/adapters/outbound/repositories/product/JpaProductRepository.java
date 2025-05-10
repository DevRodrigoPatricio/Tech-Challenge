package com.fiap.techChallenge.adapters.outbound.repositories.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.techChallenge.adapters.outbound.entities.ProductEntity;
import com.fiap.techChallenge.domain.enums.ProductStatus;

public interface JpaProductRepository extends JpaRepository<ProductEntity, UUID> {

    Optional<ProductEntity> findByName(String name);

    List<ProductEntity> findByStatus(ProductStatus status);

    List<ProductEntity> findByCategory_Id(UUID id);

    List<ProductEntity> findByStatusAndCategory_Id(ProductStatus status, UUID id);

}
