package com.fiap.techChallenge.adapters.outbound.repositories.category;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.techChallenge.adapters.outbound.entities.CategoryEntity;

public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    Optional<CategoryEntity> findByName(String name);

}
