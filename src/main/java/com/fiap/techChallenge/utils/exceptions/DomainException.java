package com.fiap.techChallenge.utils.exceptions;

import java.util.Objects;

public abstract class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(buildMessage(message));
    }

    private static String buildMessage(String message) {
        Objects.requireNonNull(message, "message é obrigatório");

        return message;
    }
}
