package com.fiap.techChallenge.application.dto.payment;

import java.util.UUID;

public class PaymentRequestDTO {

    private UUID orderId;

    public PaymentRequestDTO() {
    }

    public PaymentRequestDTO(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
}
