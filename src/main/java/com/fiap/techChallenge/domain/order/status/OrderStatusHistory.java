package com.fiap.techChallenge.domain.order.status;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.OrderStatus;

public class OrderStatusHistory {

    private UUID id;

    private UUID orderId;

    private OrderStatus status;

    private LocalDateTime date;

    public OrderStatusHistory() {
    }

    public OrderStatusHistory(UUID orderId, OrderStatus status, LocalDateTime date) {
        this.orderId = orderId;
        this.status = status;
        this.date = date;
    }

    public OrderStatusHistory(UUID id, UUID orderId, OrderStatus status, LocalDateTime date) {
        this.id = id;
        this.orderId = orderId;
        this.status = status;
        this.date = date;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public static OrderStatusHistory empty() {
        return new OrderStatusHistory();
    }
}
