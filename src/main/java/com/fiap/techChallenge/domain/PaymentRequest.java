package com.fiap.techChallenge.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentRequest {
    private UUID orderId;
    private BigDecimal amount;


    public PaymentRequest(){}

    public PaymentRequest(UUID orderId, BigDecimal amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
