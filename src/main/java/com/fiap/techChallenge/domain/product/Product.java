package com.fiap.techChallenge.domain.product;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;

    private BigDecimal amount;

    private String category;

    private String image;

    public Product(String name, String description, BigDecimal price, BigDecimal amount, String category, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.category = category;
        this.image = image;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
