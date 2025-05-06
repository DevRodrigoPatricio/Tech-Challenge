package com.fiap.techChallenge.utils.exceptions;

public class InvalidOrderStatusTransitionException extends DomainException {
    public InvalidOrderStatusTransitionException(String message) {
        super(message);
    }
}
