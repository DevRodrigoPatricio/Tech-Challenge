package com.fiap.techChallenge.adapters.outbound.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    protected UUID id;

    @Column(name = "name")
    protected String name;

    @Column(name = "email", unique = true)
    protected String email;

    @Embedded
    @AttributeOverride(
            name = "number",
            column = @Column(name = "cpf", unique = true)
    )
    protected CPFEmbeddable cpf;

    protected UserEntity() {}

    protected UUID getId() {
        return id;
    }

    protected void setId(UUID id) {
        this.id = id;
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

    protected CPFEmbeddable getCpf() {
        return cpf;
    }

    protected void setCpf(String cpfNumber) {
        var cpf = new CPFEmbeddable();
        cpf.setNumber(cpfNumber);

        this.cpf = cpf;
    }
}
