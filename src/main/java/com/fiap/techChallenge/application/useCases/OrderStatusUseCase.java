package com.fiap.techChallenge.application.useCases;

import com.fiap.techChallenge.domain.order.Order;

import java.util.UUID;

public interface OrderStatusUseCase {
    Order preparation(UUID orderId);
    Order ready(UUID orderId);
    Order delivered(UUID orderId);
    Order finished(UUID orderId);
    Order getStatus(UUID orderId);
    Order updateStatus(UUID orderId, String status);
}
