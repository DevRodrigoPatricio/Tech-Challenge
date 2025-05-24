package com.fiap.techChallenge.domain.order.projection;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.OrderStatus;

public interface OrderWithStatusAndWaitMinutesProjection {

    UUID getOrderId();

    OrderStatus getStatus();

    LocalDateTime getStatusDt();

    UUID getCustomerId();

    String getCustomerName();

    UUID getAttendantId();

    LocalDateTime getOrderDt();

    int getWaitTimeMinutes();
}
