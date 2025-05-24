package com.fiap.techChallenge.application.useCases.notification;

import java.util.UUID;

public interface NotificationStatusUseCase {

    void notifyStatus(String toEmail, UUID orderId, String status);
}
