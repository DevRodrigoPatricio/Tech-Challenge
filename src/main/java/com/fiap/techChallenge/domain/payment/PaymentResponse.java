package com.fiap.techChallenge.domain.payment;

import java.util.UUID;

public class PaymentResponse {
    private UUID orderId;
    private String status;
    private String  qrCodeImage;

    public PaymentResponse() {
    }

    public PaymentResponse(String status,UUID orderId, String qrCodeImage) {
     this.status = status;
     this.orderId = orderId;
     this.qrCodeImage = qrCodeImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getQrCodeImage() {
        return qrCodeImage;
    }

    public void setQrCodeImage(String qrCodeImage) {
        this.qrCodeImage = qrCodeImage;
    }
}
