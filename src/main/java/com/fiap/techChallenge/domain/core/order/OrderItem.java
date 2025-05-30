package com.fiap.techChallenge.domain.core.order;

import java.math.BigDecimal;
import java.util.UUID;

import com.fiap.techChallenge.domain.core.product.Product;
import com.fiap.techChallenge.domain.enums.Category;

public class OrderItem {

    private UUID productId;

    private String productName;

    private int quantity;

    private BigDecimal unitPrice;

    private Category category;

    public OrderItem() {
    }

    public OrderItem(UUID productId, String productName, int quantity, BigDecimal unitPrice, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    public OrderItem(Product product, int quantity) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.unitPrice = product.getPrice();
        this.category = product.getCategory();
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return this.productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
