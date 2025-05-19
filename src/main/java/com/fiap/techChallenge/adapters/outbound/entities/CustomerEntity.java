package com.fiap.techChallenge.adapters.outbound.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_customer")
public class CustomerEntity extends UserEntity {

    @OneToMany(mappedBy = "customer")
    private List<OrderEntity> orders;

    @Column(nullable = false)
    private boolean anonymous;

    public CustomerEntity() {}

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public UUID getId() {
        return super.getId();
    }

    public void setId(UUID id) {
        super.setId(id);
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public String getEmail() {
        return super.getEmail();
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }

    public CPFEmbeddable getCpf() {
        return super.getCpf();
    }

    public void setCpf(String cpfNumber) {
        super.setCpf(cpfNumber);
    }
}
