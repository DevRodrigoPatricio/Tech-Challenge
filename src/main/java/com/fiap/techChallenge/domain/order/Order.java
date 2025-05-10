package com.fiap.techChallenge.domain.order;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fiap.techChallenge.utils.exceptions.InvalidOrderStatusTransitionException;

public class Order {

    private final UUID id;
    private OrderStatus status;
    private LocalDateTime startPreparation;
    private LocalDateTime readySchedule;
    private LocalDateTime deliveryTime;
    private LocalDateTime finalizedSchedule;

    public Order(UUID id, OrderStatus status) {
        this.id = id;
        this.status = status;
    }

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

    public UUID getId() {
        return id;
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

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public LocalDateTime getFinalizedSchedule() {
        return finalizedSchedule;
    }

    public void setFinalizedSchedule(LocalDateTime finalizedSchedule) {
        this.finalizedSchedule = finalizedSchedule;
    }

    public void preparation() {
        if (this.status != OrderStatus.RECEIVED) {
            throw new InvalidOrderStatusTransitionException(
                    String.format("Não é possível iniciar preparo. Status atual: %s (Requerido: %s)",
                            this.status, OrderStatus.RECEIVED));
        }
        this.status = OrderStatus.IN_PREPARATION;
        this.startPreparation = LocalDateTime.now();
    }

    public void ready() {
        if (this.status != OrderStatus.IN_PREPARATION) {
            throw new InvalidOrderStatusTransitionException(
                    String.format("Não é possível marcar como pronto. Status atual: %s (Requerido: %s)",
                            this.status, OrderStatus.IN_PREPARATION));
        }
        this.status = OrderStatus.READY;
        this.readySchedule = LocalDateTime.now();
    }

    public void delivered() {
        if (this.status != OrderStatus.READY) {
            throw new InvalidOrderStatusTransitionException(
                    String.format("Não é possível marcar como entregue. Status atual: %s (Requerido: %s)",
                            this.status, OrderStatus.READY));
        }
        this.status = OrderStatus.DELIVERED;
        this.deliveryTime = LocalDateTime.now();
    }

    public void finished() {
        if (this.status != OrderStatus.DELIVERED) {
            throw new InvalidOrderStatusTransitionException(
                    String.format("Não é possível finalizar. Status atual: %s (Requerido: %s)",
                            this.status, OrderStatus.DELIVERED));
        }
        this.status = OrderStatus.FINISHED;
        this.finalizedSchedule = LocalDateTime.now();
    }


}