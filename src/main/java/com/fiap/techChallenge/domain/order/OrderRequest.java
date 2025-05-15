package com.fiap.techChallenge.domain.order;

import jakarta.persistence.*;

import java.util.List;

public class OrderRequest {

    @Column(name = "id", nullable = false)
    private List<OrderItem> items;

    @Column(name = "clientId", nullable = false)
    private String clientId;

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

}
