package com.fiap.techChallenge.adapters.outbound.entities.order;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.OrderStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "order_status_history")
public class OrderStatusHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "orderId", nullable = false)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public OrderStatusHistoryEntity() {
    }

    public OrderStatusHistoryEntity(UUID id, UUID orderId, OrderStatus status, LocalDateTime date) {
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

}
