package com.fiap.techChallenge.utils.exceptions;

public class NameAlreadyRegisteredException extends DomainException {

    public NameAlreadyRegisteredException(String name) {
        super(String.format("O nome '%s' já foi cadastrado", name));
    }
}
