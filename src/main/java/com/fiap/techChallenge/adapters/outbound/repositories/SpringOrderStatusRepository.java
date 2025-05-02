package com.fiap.techChallenge.adapters.outbound.repositories;

import com.fiap.techChallenge.adapters.outbound.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringOrderStatusRepository extends JpaRepository<OrderEntity, UUID> {
}