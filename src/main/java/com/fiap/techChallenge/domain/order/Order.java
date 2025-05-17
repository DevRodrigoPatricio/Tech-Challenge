package com.fiap.techChallenge.domain.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {

    private UUID id;

    private List<OrderItem> items;

    //Vai ser alterado para ser a entidade cliente
    private String clientId;

    //Vai ser alterado para ser a entidade atendente
    private String attendantId;

    private BigDecimal price;

    private LocalDateTime orderDt;

    public Order() {
    }

    public Order(List<OrderItem> items, String clientId, String attendantId, BigDecimal price, LocalDateTime orderDt) {
        this.items = items;
        this.clientId = clientId;
        this.attendantId = attendantId;
        this.price = price;
        this.orderDt = orderDt;
    }

    public Order(UUID id, List<OrderItem> items, String clientId, String attendantId, BigDecimal price, LocalDateTime orderDt) {
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

    public List<OrderItem> getItems() {
        return this.items;
    }

    public void setItems(List<OrderItem> items) {
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

    public static Order empty() {
        return new Order();
    }
}
