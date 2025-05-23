package com.fiap.techChallenge.application.dto.order;

import java.util.List;
import java.util.UUID;

import com.fiap.techChallenge.domain.order.OrderItem;
import jakarta.persistence.Column;

public class OrderRequestDTO {

    @Column(name = "id", nullable = false)
    private List<OrderItem> items;

    private UUID customerId;

    public List<OrderItem> getItems() {
        return this.items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public UUID getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(UUID clientId) {
        this.customerId = clientId;
    }

}
