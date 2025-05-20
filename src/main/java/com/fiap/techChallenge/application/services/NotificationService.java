package com.fiap.techChallenge.application.services;

import com.fiap.techChallenge.adapters.outbound.storage.order.status.notification.NotifierPort;
import com.fiap.techChallenge.application.useCases.NotificationStatusUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService implements NotificationStatusUseCase {

    private final NotifierPort notifier;

    @Override
    public void notifyStatus(String toEmail, UUID orderId, String status) {
        notifier.sendStatus(toEmail, orderId, status);
    }
}
