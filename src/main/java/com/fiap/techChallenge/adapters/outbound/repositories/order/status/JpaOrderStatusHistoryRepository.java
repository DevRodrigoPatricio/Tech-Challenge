package com.fiap.techChallenge.adapters.outbound.repositories.order.status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiap.techChallenge.adapters.outbound.entities.OrderStatusHistoryEntity;
import com.fiap.techChallenge.domain.enums.OrderStatus;

@Repository
public interface JpaOrderStatusHistoryRepository extends JpaRepository<OrderStatusHistoryEntity, UUID> {

    boolean existsByOrderIdAndStatus(UUID orderId, OrderStatus status);

    List<OrderStatusHistoryEntity> findAllByOrderIdOrderByDateDesc(UUID orderId);

    Optional<OrderStatusHistoryEntity> findFirstByOrderIdOrderByDateDesc(UUID orderId);

}
