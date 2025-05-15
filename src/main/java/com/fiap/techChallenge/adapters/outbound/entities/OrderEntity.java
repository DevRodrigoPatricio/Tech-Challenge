package com.fiap.techChallenge.adapters.outbound.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "`order`")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ElementCollection
    @CollectionTable(name = "orderItems", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItemEmbeddable> items;

    //Vai ser alterado para ser a entidade cliente
    @Column(name = "clientId", nullable = false)
    private String clientId;

    //Vai ser alterado para ser a entidade atendente
    @Column(name = "attendantId")
    private String attendantId;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "orderDt")
    private LocalDateTime orderDt;

    public OrderEntity() {
    }

    public OrderEntity(UUID id, List<OrderItemEmbeddable> items, String clientId, String attendantId, BigDecimal price, LocalDateTime orderDt) {
        this.id = id;
        this.items = items;
        this.clientId = clientId;
        this.attendantId = attendantId;
        this.price = price;
        this.orderDt = orderDt;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<OrderItemEmbeddable> getItems() {
        return this.items;
    }

    public void setItems(List<OrderItemEmbeddable> items) {
        this.items = items;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAttendantId() {
        return this.attendantId;
    }

    public void setAttendantId(String attendantId) {
        this.attendantId = attendantId;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getOrderDt() {
        return this.orderDt;
    }

    public void setOrderDt(LocalDateTime orderDt) {
        this.orderDt = orderDt;
    }
}
