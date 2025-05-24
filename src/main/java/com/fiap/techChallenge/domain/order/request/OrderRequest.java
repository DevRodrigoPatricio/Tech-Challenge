package com.fiap.techChallenge.domain.order.request;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;

public class OrderRequest {

    @Column(name = "items", nullable = false)
    private List<OrderItemRequest> items;

    @Column(name = "customerId", nullable = false)
    private UUID customerId;

    public List<OrderItemRequest> getItems() {
        return this.items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }

    public UUID getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(UUID clientId) {
        this.customerId = clientId;
    }

}
