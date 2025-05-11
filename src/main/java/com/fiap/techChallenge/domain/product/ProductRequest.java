package com.fiap.techChallenge.domain.product;

import java.math.BigDecimal;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.ProductStatus;

public class ProductRequest {

    private String name;

    private String description;

    private BigDecimal price;

    private UUID categoryId;

    private ProductStatus status;

    private String image;

    public ProductRequest(String name, String description, BigDecimal price, UUID categoryId, ProductStatus status, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.status = status;
        this.image = image;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UUID getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public ProductStatus getStatus() {
        return this.status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
