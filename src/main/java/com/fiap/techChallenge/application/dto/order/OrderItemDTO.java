package com.fiap.techChallenge.application.dto.order;

import java.util.UUID;


public class OrderItemDTO{

    private UUID productId;
    private int quantity;

    public OrderItemDTO() {
    }

    public OrderItemDTO(UUID productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return this.productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
