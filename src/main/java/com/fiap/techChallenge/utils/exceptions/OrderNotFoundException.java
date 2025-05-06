package com.fiap.techChallenge.utils.exceptions;

import java.util.UUID;

public class OrderNotFoundException extends DomainException {
    public OrderNotFoundException(UUID orderId) {
        super("Order not found with id: " + orderId);
    }
}

