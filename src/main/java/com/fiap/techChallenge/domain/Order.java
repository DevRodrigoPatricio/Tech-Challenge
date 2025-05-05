package com.fiap.techChallenge.domain;

import com.fiap.techChallenge.domain.exceptions.InvalidOrderStatusTransitionException;
import java.time.LocalDateTime;
import java.util.UUID;

public class Order {

    private final UUID id;
    private OrderStatus status;
    private LocalDateTime inicioPreparo;
    private LocalDateTime horarioPronto;
    private LocalDateTime horarioEntregue;
    private LocalDateTime horarioFinalizado;

    public Order(UUID id, OrderStatus status) {
        this.id = id;
        this.status = status;
    }

    public enum OrderStatus {
        RECEBIDO,
        EM_PREPARACAO,
        PRONTO,
        ENTREGUE,
        FINALIZADO,
        PAGO,
        NAO_PAGO,
        PAGAMENTO_PENDENTE

    }

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getInicioPreparo() {
        return inicioPreparo;
    }

    public LocalDateTime getHorarioPronto() {
        return horarioPronto;
    }

    public LocalDateTime getHorarioEntregue() {
        return horarioEntregue;
    }

    public LocalDateTime getHorarioFinalizado() {
        return horarioFinalizado;
    }

    public void setInicioPreparo(LocalDateTime inicioPreparo) {
        this.inicioPreparo = inicioPreparo;
    }

    public void setHorarioPronto(LocalDateTime horarioPronto) {
        this.horarioPronto = horarioPronto;
    }

    public void setHorarioEntregue(LocalDateTime horarioEntregue) {
        this.horarioEntregue = horarioEntregue;
    }

    public void setHorarioFinalizado(LocalDateTime horarioFinalizado) {
        this.horarioFinalizado = horarioFinalizado;
    }

    public void preparo() {
        if (this.status != OrderStatus.RECEBIDO) {
            throw new InvalidOrderStatusTransitionException(
                    String.format("Não é possível iniciar preparo. Status atual: %s (Requerido: %s)",
                            this.status, OrderStatus.RECEBIDO));
        }
        this.status = OrderStatus.EM_PREPARACAO;
        this.inicioPreparo = LocalDateTime.now();
    }

    public void pronto() {
        if (this.status != OrderStatus.EM_PREPARACAO) {
            throw new InvalidOrderStatusTransitionException(
                    String.format("Não é possível marcar como pronto. Status atual: %s (Requerido: %s)",
                            this.status, OrderStatus.EM_PREPARACAO));
        }
        this.status = OrderStatus.PRONTO;
        this.horarioPronto = LocalDateTime.now();
    }

    public void entregue() {
        if (this.status != OrderStatus.PRONTO) {
            throw new InvalidOrderStatusTransitionException(
                    String.format("Não é possível marcar como entregue. Status atual: %s (Requerido: %s)",
                            this.status, OrderStatus.PRONTO));
        }
        this.status = OrderStatus.ENTREGUE;
        this.horarioEntregue = LocalDateTime.now();
    }

    public void finalizado() {
        if (this.status != OrderStatus.ENTREGUE) {
            throw new InvalidOrderStatusTransitionException(
                    String.format("Não é possível finalizar. Status atual: %s (Requerido: %s)",
                            this.status, OrderStatus.ENTREGUE));
        }
        this.status = OrderStatus.FINALIZADO;
        this.horarioFinalizado = LocalDateTime.now();
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}