package com.fiap.techChallenge.utils.exceptions;

public class UserAlreadyExistsException extends DomainException {
    public UserAlreadyExistsException() {
        super("Este usuário já existe.");
    }
}
