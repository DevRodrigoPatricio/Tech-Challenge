package com.fiap.techChallenge.domain.order.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.domain.order.projection.OrderItemProjection;

public record OrderWithItemsAndStatusDTO(
        UUID orderId,
        OrderStatus status,
        LocalDateTime statusDt,
        UUID attendantId,
        UUID customerId,
        String customerName,
        LocalDateTime orderDt,
        List<OrderItemProjection> items
        ) {

}
