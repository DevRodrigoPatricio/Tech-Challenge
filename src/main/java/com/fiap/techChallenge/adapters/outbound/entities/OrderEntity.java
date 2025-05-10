package com.fiap.techChallenge.adapters.outbound.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity {

    public enum OrderStatus {
        RECEIVED,
        IN_PREPARATION,
        READY,
        DELIVERED,
        FINISHED,
        PAID,
        NOT_PAID,
        PAYMENT_PENDING
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "inicio_preparo")
    private LocalDateTime startPreparation;

    @Column(name = "horario_pronto")
    private LocalDateTime readySchedule;

    @Column(name = "horario_entregue")
    private LocalDateTime deliveryTime;

    @Column(name = "horario_finalizado")
    private LocalDateTime finalizedSchedule;

    public OrderEntity() {
    }

    public OrderEntity(UUID id, OrderStatus status,
                       LocalDateTime startPreparation,
                       LocalDateTime readySchedule,
                       LocalDateTime deliveryTime,
                       LocalDateTime finalizedSchedule ){
        this.id = id;
        this.status = status;
        this.startPreparation = startPreparation;
        this.readySchedule = readySchedule;
        this.deliveryTime = deliveryTime;
        this.finalizedSchedule = finalizedSchedule;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartPreparation() {
        return startPreparation;
    }

    public void setStartPreparation(LocalDateTime startPreparation) {
        this.startPreparation = startPreparation;
    }

    public LocalDateTime getReadySchedule() {
        return readySchedule;
    }

    public void setReadySchedule(LocalDateTime readySchedule) {
        this.readySchedule = readySchedule;
    }

    public LocalDateTime getFinalizedSchedule() {
        return finalizedSchedule;
    }

    public void setFinalizedSchedule(LocalDateTime finalizedSchedule) {
        this.finalizedSchedule = finalizedSchedule;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}