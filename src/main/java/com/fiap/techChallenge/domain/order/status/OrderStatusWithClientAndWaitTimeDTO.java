package com.fiap.techChallenge.domain.order.status;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.OrderStatus;

public record OrderStatusWithClientAndWaitTimeDTO(
        UUID orderId,
        OrderStatus status,
        LocalDateTime statusDate,
        String clientId,
        LocalDateTime orderDate,
        int waitTimeMinutes
        ) {

}
