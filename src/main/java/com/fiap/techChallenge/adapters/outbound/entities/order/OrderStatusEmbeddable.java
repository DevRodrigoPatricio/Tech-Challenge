package com.fiap.techChallenge.adapters.outbound.entities.order;

import com.fiap.techChallenge.domain.enums.OrderStatus;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.util.UUID;

@Embeddable
public class OrderStatusEmbeddable {

    private UUID attendantId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime date;

    public OrderStatusEmbeddable() {
    }

    public OrderStatusEmbeddable(UUID attendantId, OrderStatus status, LocalDateTime date) {
        this.status = status;
        this.attendantId = attendantId;
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public UUID getAttendantId() {
        return this.attendantId;
    }

    public void setAttendantId(UUID attendantId) {
        this.attendantId = attendantId;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
