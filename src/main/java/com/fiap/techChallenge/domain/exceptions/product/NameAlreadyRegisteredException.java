package com.fiap.techChallenge.domain.exceptions.product;

import com.fiap.techChallenge.domain.exceptions.DomainException;

public class NameAlreadyRegisteredException extends DomainException {

    public NameAlreadyRegisteredException(String name) {
        super(String.format("O nome '%s' já foi cadastrado", name));
    }
}
