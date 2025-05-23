package com.fiap.techChallenge.domain.order;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.OrderStatus;

public record OrderWithStatusDTO(
        UUID orderId,
        OrderStatus status,
        LocalDateTime statusDt,
        UUID customerId,
        String customerName,
        LocalDateTime orderDt,
        int waitTimeMinutes
        ) {

}
