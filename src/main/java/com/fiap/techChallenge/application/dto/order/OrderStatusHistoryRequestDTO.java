package com.fiap.techChallenge.application.dto.order;

import java.util.UUID;

import com.fiap.techChallenge.domain.enums.OrderStatus;

public class OrderStatusHistoryRequestDTO {

    private UUID orderId;

    private OrderStatus status;

    public OrderStatusHistoryRequestDTO() {
    }

    public OrderStatusHistoryRequestDTO(UUID orderId, OrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }

    public UUID getOrderId() {
        return this.orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

}
