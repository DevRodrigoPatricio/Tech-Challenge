package com.fiap.techChallenge.domain.exceptions;

public class InvalidOrderStatusTransitionException extends DomainException {
    public InvalidOrderStatusTransitionException(String message) {
        super(message);
    }
}
