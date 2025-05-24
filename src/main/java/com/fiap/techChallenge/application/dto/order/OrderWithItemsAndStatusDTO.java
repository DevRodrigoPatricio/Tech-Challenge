package com.fiap.techChallenge.application.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.application.dto.order.projection.OrderItemProjection;

public record OrderWithItemsAndStatusDTO(
        UUID orderId,
        OrderStatus status,
        LocalDateTime statusDt,
        UUID attendantId,
        String attendantName,
        UUID customerId,
        String customerName,
        BigDecimal price,
        LocalDateTime orderDt,
        List<OrderItemProjection> items
        ) {

}
