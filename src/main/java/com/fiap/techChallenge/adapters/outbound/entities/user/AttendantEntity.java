package com.fiap.techChallenge.adapters.outbound.entities.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "user_attendant")
public class AttendantEntity extends UserEntity {

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
