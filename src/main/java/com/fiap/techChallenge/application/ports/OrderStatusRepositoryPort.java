package com.fiap.techChallenge.application.ports;

import com.fiap.techChallenge.domain.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderStatusRepositoryPort {
    Optional<Order> findById(UUID orderId);
    Order save(Order order);

    void updateOrderStatus(UUID orderId, Order.OrderStatus orderStatus);

}