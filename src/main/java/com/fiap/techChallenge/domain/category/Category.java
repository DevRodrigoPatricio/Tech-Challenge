package com.fiap.techChallenge.domain.category;

import java.util.UUID;

public class Category {

    private UUID id;

    private String name;

    public Category(String name) {
        this.name = name;
    }

    public Category() {
        
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

}
