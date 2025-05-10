package com.fiap.techChallenge.adapters.outbound.entities;

import java.math.BigDecimal;
import java.util.UUID;

import com.fiap.techChallenge.domain.product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "nome")
    private String name;

    @Column(name = "descricao")
    private String description;

    @Column(name = "preco")
    private BigDecimal price;

    @Column(name = "quantidade")
    private BigDecimal amount;

    @Column(name = "categoria")
    private String category;

    @Column(name = "imagem")
    private String image;

    public ProductEntity(UUID id, String name, String description, BigDecimal price, BigDecimal amount, String category, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.category = category;
        this.image = image;
    }

    public ProductEntity(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.amount = product.getAmount();
        this.category = product.getCategory();
        this.image = product.getImage();
    }

    public ProductEntity() {
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
