package com.fiap.techChallenge.adapters.outbound.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity {

    public enum OrderStatus {
        RECEBIDO,
        EM_PREPARACAO,
        PRONTO,
        ENTREGUE,
        FINALIZADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "inicio_preparo")
    private LocalDateTime inicioPreparo;

    @Column(name = "horario_pronto")
    private LocalDateTime horarioPronto;

    @Column(name = "horario_entregue")
    private LocalDateTime horarioEntregue;

    @Column(name = "horario_finalizado")
    private LocalDateTime horarioFinalizado;

    public OrderEntity() {
    }

    public OrderEntity(UUID id, OrderStatus status,
                       LocalDateTime inicioPreparo,
                       LocalDateTime horarioPronto,
                       LocalDateTime horarioEntregue,
                       LocalDateTime horarioFinalizado ){
        this.id = id;
        this.status = status;
        this.inicioPreparo = inicioPreparo;
        this.horarioPronto = horarioPronto;
        this.horarioEntregue = horarioEntregue;
        this.horarioFinalizado = horarioFinalizado;
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

    public LocalDateTime getInicioPreparo() {
        return inicioPreparo;
    }

    public void setInicioPreparo(LocalDateTime inicioPreparo) {
        this.inicioPreparo = inicioPreparo;
    }

    public LocalDateTime getHorarioPronto() {
        return horarioPronto;
    }

    public void setHorarioPronto(LocalDateTime horarioPronto) {
        this.horarioPronto = horarioPronto;
    }

    public LocalDateTime getHorarioEntregue() {
        return horarioEntregue;
    }

    public void setHorarioEntregue(LocalDateTime horarioEntregue) {
        this.horarioEntregue = horarioEntregue;
    }

    public LocalDateTime getHorarioFinalizado() {
        return horarioFinalizado;
    }

    public void setHorarioFinalizado(LocalDateTime horarioFinalizado) {
        this.horarioFinalizado = horarioFinalizado;
    }
}