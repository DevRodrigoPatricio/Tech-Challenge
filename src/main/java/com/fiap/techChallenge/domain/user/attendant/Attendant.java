package com.fiap.techChallenge.domain.user.attendant;

import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.user.CPF;
import com.fiap.techChallenge.domain.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Attendant extends User {

    private List<Order> managedOrders;

    public Attendant(UUID id, String name, String email, CPF cpf) {
        super(id, name, email, cpf);
        this.managedOrders = new ArrayList<>();
    }

    public static Attendant create(UUID id, String name, String email, String cpfNumber) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome obrigatório");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email obrigatório");
        if (cpfNumber == null || cpfNumber.isBlank()) throw new IllegalArgumentException("CPF obrigatório");

        CPF cpf = new CPF(cpfNumber);
        return new Attendant(id, name, email, cpf);
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
