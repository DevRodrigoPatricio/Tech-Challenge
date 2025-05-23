package com.fiap.techChallenge.domain.exceptions.user;

import com.fiap.techChallenge.domain.exceptions.DomainException;

public class UserAlreadyExistsException extends DomainException {
    public UserAlreadyExistsException() {
        super("Este usuário já existe.");
    }
}
