package com.fiap.techChallenge.adapters.outbound.repositories.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fiap.techChallenge.adapters.outbound.entities.CategoryEntity;

public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    Optional<CategoryEntity> findByName(String name);

    Optional<CategoryEntity> findTopByOrderByDisplayOrderDesc();

    List<CategoryEntity> findAllByOrderByDisplayOrderAsc();

    List<CategoryEntity> findByDisplayOrderGreaterThanEqualOrderByDisplayOrderAsc(int displayOrder);

    @Query("SELECT c FROM CategoryEntity c WHERE c.displayOrder BETWEEN :start AND :end ORDER BY c.displayOrder ASC")
    List<CategoryEntity> findByDisplayOrderRange(@Param("start") int start, @Param("end") int end);

}
