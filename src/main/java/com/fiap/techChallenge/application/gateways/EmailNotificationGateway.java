package com.fiap.techChallenge.application.gateways;

import java.util.UUID;

public interface EmailNotificationGateway {
    void sendEmail(String toEmail, UUID orderId, String status);
}
