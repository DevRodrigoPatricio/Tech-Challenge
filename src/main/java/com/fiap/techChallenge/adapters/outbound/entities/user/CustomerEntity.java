package com.fiap.techChallenge.adapters.outbound.entities.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "user_customer")
public class CustomerEntity extends UserEntity {

    @Column(nullable = false)
    private boolean anonymous;

    public CustomerEntity() {
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    @Override
    public UUID getId() {
        return super.getId();
    }

    @Override
    public void setId(UUID id) {
        super.setId(id);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public CPFEmbeddable getCpf() {
        return super.getCpf();
    }

    @Override
    public void setCpf(String cpfNumber) {
        super.setCpf(cpfNumber);
    }
}
