package com.fiap.techChallenge.domain;

public class PaymentResponse {
    private String qrCode;
    private String status;

    public PaymentResponse() {
    }

    public PaymentResponse(String qrCode, String status) {
        this.status = status;
        this.qrCode = qrCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
