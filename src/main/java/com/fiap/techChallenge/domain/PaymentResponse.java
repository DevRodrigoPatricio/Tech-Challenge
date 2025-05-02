package com.fiap.techChallenge.domain;

public class PaymentResponse {
    private String qrCode;
    private String status;
    private String qrCodeBase64;

    public PaymentResponse() {
    }

    public PaymentResponse(String qrCode, String status) {
        this.status = status;
        this.qrCode = qrCode;
    }

    public PaymentResponse(String pending, String qrCode, String qrCodeBase64) {
     this.status = pending;
     this.qrCode = qrCode;
     this.qrCodeBase64 = qrCodeBase64;
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

    public String getQrCodeBase64() {
        return qrCodeBase64;
    }

    public void setQrCodeBase64(String qrCodeBase64) {
        this.qrCodeBase64 = qrCodeBase64;
    }


}
