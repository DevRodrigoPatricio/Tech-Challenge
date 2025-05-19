package com.fiap.techChallenge.domain.user.customer;

import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.user.CPF;
import com.fiap.techChallenge.domain.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer extends User {

    private final boolean anonymous;
    private List<Order> orders;

    public Customer(UUID id, String name, String email, CPF cpf, boolean anonymous) {
        super(id, name, email, cpf);
        this.anonymous = anonymous;
        this.orders = new ArrayList<>();
    }

    public static Customer create(UUID id, String name, String email, String cpfNumber, boolean anonymous) {

        if (!anonymous) {
            if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome obrigatório");
            if (email == null || email.isBlank()) throw new IllegalArgumentException("Email obrigatório");
            if (cpfNumber == null || cpfNumber.isBlank()) throw new IllegalArgumentException("CPF obrigatório");

            CPF cpf = new CPF(cpfNumber);

            return new Customer(id, name, email, cpf, false);
        } else {
            return new Customer(id, null, null, null, true);
        }
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public UUID getId() {
        return super.getId();
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

    public String getCpf() {
        return super.getCpf();
    }

    public void setCpf(String cpfNumber) {
        super.setCpf(cpfNumber);
    }
}
