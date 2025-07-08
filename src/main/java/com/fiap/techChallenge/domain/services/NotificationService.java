package com.fiap.techChallenge.domain.services;

import java.util.UUID;

public interface NotificationService {
    void notifyStatus(String toEmail, UUID orderId, String status);
}
