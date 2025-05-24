package com.fiap.techChallenge.domain.order.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.OrderStatus;

public interface OrderWithStatusProjection {

    UUID getOrderId();

    OrderStatus getStatus();

    LocalDateTime getStatusDt();

    UUID getCustomerId();

    String getCustomerName();

    UUID getAttendantId();

    String getAttendantName();

    BigDecimal getPrice();

    LocalDateTime getOrderDt();
}
