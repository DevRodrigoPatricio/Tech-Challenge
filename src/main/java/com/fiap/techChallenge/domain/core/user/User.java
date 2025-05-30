package com.fiap.techChallenge.domain.core.user;

import java.util.Objects;
import java.util.UUID;

public class User {

    protected UUID id;
    protected String name;
    protected String email;
    protected CPF cpf;

    protected User() {
    }

    protected User(UUID id, String name, String email, CPF cpf) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
    }

    protected UUID getId() {
        return id;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected String getEmail() {
        return email;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    protected void setCpf(String cpfNumber) {
        this.cpf = new CPF(cpfNumber);
    }

    protected String getFormattedCpf() {
        return Objects.requireNonNullElseGet(cpf, CPF::new).getFormattedNumber();
    }

    protected String getUnformattedCpf() {
        return Objects.requireNonNullElseGet(cpf, CPF::new).getUnformattedNumber();
    }
}
