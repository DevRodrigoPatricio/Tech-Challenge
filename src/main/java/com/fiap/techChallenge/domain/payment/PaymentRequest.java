package com.fiap.techChallenge.domain.payment;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentRequest {
    private UUID orderId;

    public PaymentRequest(){}

    public PaymentRequest(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
}
