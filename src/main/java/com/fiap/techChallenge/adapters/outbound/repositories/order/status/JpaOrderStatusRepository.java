package com.fiap.techChallenge.adapters.outbound.repositories.order.status;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiap.techChallenge.adapters.outbound.entities.OrderEntity;

@Repository
public interface JpaOrderStatusRepository extends JpaRepository<OrderEntity, UUID> {

}
