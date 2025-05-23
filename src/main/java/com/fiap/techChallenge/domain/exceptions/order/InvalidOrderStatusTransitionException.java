package com.fiap.techChallenge.domain.exceptions.order;

import com.fiap.techChallenge.domain.exceptions.DomainException;

public class InvalidOrderStatusTransitionException extends DomainException {
    public InvalidOrderStatusTransitionException(String message) {
        super(message);
    }
}
