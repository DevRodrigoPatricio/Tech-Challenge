package com.fiap.techChallenge.domain.core.product;

import java.math.BigDecimal;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.Category;
import com.fiap.techChallenge.domain.enums.ProductStatus;

public class Product {

    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;

    private Category category;

    private ProductStatus status;

    private String image;

    public Product() {

    }

    public Product(UUID id, String name, String description, BigDecimal price, Category category, ProductStatus status, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.status = status;
        this.image = image;
    }

    public Product(String name, String description, BigDecimal price, Category category, ProductStatus status, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
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

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public static Product empty() {
        return new Product();
    }

}
