package com.fiap.techChallenge.domain.core.order;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.OrderStatus;

public class OrderStatusHistory {

    private UUID attendantId;

    private OrderStatus status;

    private LocalDateTime date;

    public OrderStatusHistory() {

    }

    public OrderStatusHistory(UUID attendantId, OrderStatus status, LocalDateTime date) {
        this.attendantId = attendantId;
        this.status = status;
        this.date = date;
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

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
