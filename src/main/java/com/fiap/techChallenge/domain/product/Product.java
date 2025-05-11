package com.fiap.techChallenge.domain.product;

import java.math.BigDecimal;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.ProductStatus;

public class Product {

    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;

    private UUID categoriaId;

    private ProductStatus status;

    private String image;

    public Product() {
        
    }

    public Product(String name, String description, BigDecimal price, UUID categoriaId, ProductStatus status, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoriaId = categoriaId;
        this.status = status;
        this.image = image;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
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
        return this.categoriaId;
    }

    public void setCategoryId(UUID categoriaId) {
        this.categoriaId = categoriaId;
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
