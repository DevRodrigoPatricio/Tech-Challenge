package com.fiap.techChallenge.domain.order;




import java.util.UUID;

import com.fiap.techChallenge.domain.enums.Category;

public class OrderItemRequest {

    private UUID productId;
    private int quantity;
    private Category category;

    public OrderItemRequest() {
    }

    public OrderItemRequest(UUID productId, int quantity, Category category) {
        this.productId = productId;
        this.quantity = quantity;
        this.category = category;
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

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
