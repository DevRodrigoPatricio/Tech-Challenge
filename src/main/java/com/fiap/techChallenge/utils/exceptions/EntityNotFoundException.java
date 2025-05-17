package com.fiap.techChallenge.utils.exceptions;

public class EntityNotFoundException extends DomainException {

    public EntityNotFoundException(String entityName) {
        super(String.format("Registro não encontrado: " + entityName));
    }
}
