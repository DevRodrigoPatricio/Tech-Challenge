package com.fiap.techChallenge.domain.user.customer;

import com.fiap.techChallenge.domain.user.CPF;
import com.fiap.techChallenge.domain.user.User;

import java.util.UUID;

public class Customer extends User {

    private final boolean anonymous;

    public Customer(UUID id, String name, String email, CPF cpf, boolean anonymous) {
        super(id, name, email, cpf);
        this.anonymous = anonymous;
    }

    public static Customer create(UUID id, String name, String email, String cpfNumber, boolean anonymous) {

        if (!anonymous) {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Nome obrigatório");
            }
            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("Email obrigatório");
            }
            if (cpfNumber == null || cpfNumber.isBlank()) {
                throw new IllegalArgumentException("CPF obrigatório");
            }

            CPF cpf = new CPF(cpfNumber);

            return new Customer(id, name, email, cpf, false);
        } else {
            return new Customer(id, null, null, null, true);
        }
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    @Override
    public UUID getId() {
        return super.getId();
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
    public String getCpf() {
        return super.getCpf();
    }

    @Override

    public void setCpf(String cpfNumber) {
        super.setCpf(cpfNumber);
    }
}
