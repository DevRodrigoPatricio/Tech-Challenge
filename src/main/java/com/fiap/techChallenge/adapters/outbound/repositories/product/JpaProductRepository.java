package com.fiap.techChallenge.adapters.outbound.repositories.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.techChallenge.adapters.outbound.entities.ProductEntity;
import com.fiap.techChallenge.domain.enums.ProductStatus;

public interface JpaProductRepository extends JpaRepository<ProductEntity, UUID> {

    Optional<ProductEntity> findByName(String name);

    List<ProductEntity> findByStatus(ProductStatus status);

    List<ProductEntity> findByCategory_Id(UUID categoryId);

    List<ProductEntity> findByStatusAndCategory_Id(ProductStatus status, UUID categoryId);

    @Transactional
    @Modifying
    void deleteByCategory_Id(UUID categoryId);

}
