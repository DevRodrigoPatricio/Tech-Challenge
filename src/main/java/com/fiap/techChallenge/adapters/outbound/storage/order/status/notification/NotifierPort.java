package com.fiap.techChallenge.adapters.outbound.storage.order.status.notification;

import java.util.UUID;

public interface NotifierPort {
    void sendStatus(String toEmail, UUID orderId, String status);
}