package com.fiap.techChallenge.adapters.outbound.repositories.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fiap.techChallenge.adapters.outbound.entities.order.OrderEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, UUID> {

    List<OrderEntity> findAllByCustomerId(UUID id);

    List<OrderEntity> findAllByOrderDtBetween(LocalDateTime startDate, LocalDateTime endDate);

}
