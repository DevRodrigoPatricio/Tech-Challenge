package com.fiap.techChallenge.domain.order.status;

import java.util.Optional;
import java.util.UUID;

import com.fiap.techChallenge.domain.order.Order;

public interface OrderStatusRepository {
    Optional<Order> findById(UUID orderId);
    Order save(Order order);

    void updateOrderStatus(UUID orderId, Order.OrderStatus orderStatus);

}