package com.fiap.techChallenge.application.dto.order;

import java.util.UUID;

import com.fiap.techChallenge.domain.enums.OrderStatus;

public class OrderStatusHistoryRequestDTO {

    private UUID orderId;

    private UUID attendantId;

    private OrderStatus status;

    public OrderStatusHistoryRequestDTO() {
    }

    public OrderStatusHistoryRequestDTO(UUID orderId, UUID attendantId, OrderStatus status) {
        this.orderId = orderId;
        this.attendantId = attendantId;
        this.status = status;
    }

    public UUID getOrderId() {
        return this.orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getAttendantId() {
        return this.attendantId;
    }

    public void setAttendantId(UUID attendantId) {
        this.attendantId = attendantId;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

}
