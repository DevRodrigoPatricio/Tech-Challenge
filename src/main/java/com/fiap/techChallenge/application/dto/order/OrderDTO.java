package com.fiap.techChallenge.application.dto.order;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;

public class OrderDTO {

    @Column(name = "items", nullable = false)
    private List<OrderItemDTO> items;

    @Column(name = "customerId", nullable = false)
    private UUID customerId;

    public List<OrderItemDTO> getItems() {
        return this.items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public UUID getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(UUID clientId) {
        this.customerId = clientId;
    }

}
