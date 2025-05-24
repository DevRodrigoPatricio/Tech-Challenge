package com.fiap.techChallenge.adapters.outbound.repositories.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.techChallenge.adapters.outbound.entities.ProductEntity;
import com.fiap.techChallenge.domain.enums.Category;
import com.fiap.techChallenge.domain.enums.ProductStatus;

@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, UUID> {

    Optional<ProductEntity> findByName(String name);

    List<ProductEntity> findAllByOrderByCategoryAsc();

    List<ProductEntity> findByStatus(ProductStatus status);

    List<ProductEntity> findByCategory(Category category);

    List<ProductEntity> findByStatusAndCategory(ProductStatus status, Category category);

    @Query("SELECT DISTINCT p.category FROM ProductEntity p WHERE p.status = :status")
    List<Category> listCategorysByProductStatus(@Param("status") ProductStatus status);

    @Transactional
    @Modifying
    void deleteByCategory(Category category);

}
