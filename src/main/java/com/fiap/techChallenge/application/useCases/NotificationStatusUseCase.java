package com.fiap.techChallenge.application.useCases;

import java.util.UUID;

public interface NotificationStatusUseCase {

    void notifyStatus(String toEmail, UUID orderId, String status);
}
