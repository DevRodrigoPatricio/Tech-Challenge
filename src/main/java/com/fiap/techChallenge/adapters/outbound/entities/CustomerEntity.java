package com.fiap.techChallenge.adapters.outbound.entities;

import com.fiap.techChallenge.domain.customer.Customer;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Embedded
    @AttributeOverride(
            name = "number",
            column = @Column(name = "cpf", unique = true)
    )
    private CPFEmbeddable cpf;

    @Column(nullable = false)
    private boolean anonymous;

    public CustomerEntity(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.cpf = new CPFEmbeddable(customer.getCpf());
        this.anonymous = customer.isAnonymous();
    }

    public CustomerEntity() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public CPFEmbeddable getCpf() {
        return cpf;
    }

    public void setCpf(String cpfNumber) {
        var cpf = new CPFEmbeddable();
        cpf.setNumber(cpfNumber);

        this.cpf = cpf;
    }

    public boolean isAnonymous() {
        return anonymous;
    }
}
