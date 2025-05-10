package com.fiap.techChallenge.domain.category;

public class CategoryRequest {

    private String name;

    public CategoryRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
