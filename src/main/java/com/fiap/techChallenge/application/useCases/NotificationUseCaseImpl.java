package com.fiap.techChallenge.application.useCases;

import com.fiap.techChallenge.application.gateways.EmailNotificationGateway;
import com.fiap.techChallenge.domain.services.NotificationService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class NotificationUseCaseImpl implements NotificationService {

    private final EmailNotificationGateway emailNotificationGateway;

    public NotificationUseCaseImpl(EmailNotificationGateway emailNotificationGateway) {
        this.emailNotificationGateway = emailNotificationGateway;
    }

    @Override
    public void notifyStatus(String toEmail, UUID orderId, String status) {
        emailNotificationGateway.sendEmail(toEmail, orderId, status);
    }
}
