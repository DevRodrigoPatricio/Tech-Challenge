package com.fiap.techChallenge.domain.customer;

import com.fiap.techChallenge.adapters.outbound.entities.CustomerEntity;

import java.util.UUID;

public class Customer {

    private UUID id;
    private String name;
    private String email;
    private CPF cpf;
    private boolean anonymous;

    public Customer(CustomerEntity customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        var cpfEmbeddable = customer.getCpf();
        this.cpf = cpfEmbeddable != null ? new CPF(cpfEmbeddable.getNumber()) : null;
        this.anonymous = customer.isAnonymous();
    }

    public Customer(CustomerRequestDTO customer) {
        this.name = customer.name();
        this.email = customer.email();
        this.cpf = new CPF(customer.cpf());
        this.anonymous = customer.anonymous();
    }

    public Customer() {}

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        if (cpf != null) {
            return cpf.getNumber();
        }
        return new CPF().getNumber();
    }

    public void setCpf(String cpfNumber) {
        var cpf = new CPF();
        cpf.setNumber(cpfNumber);

        this.cpf = cpf;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }
}
