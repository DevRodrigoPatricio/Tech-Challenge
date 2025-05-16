package com.fiap.techChallenge.domain.order;

import jakarta.persistence.*;


public class OrderRequest {

    @Column(name = "clientId", nullable = false)
    private String clientId;

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

}
