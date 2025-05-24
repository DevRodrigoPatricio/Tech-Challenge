package com.fiap.techChallenge.adapters.outbound.entities.user;

import com.fiap.techChallenge.adapters.outbound.entities.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "user_attendant")
public class AttendantEntity extends UserEntity {

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
