package com.fiap.techChallenge;

import com.fiap.techChallenge.application.gateways.EmailNotificationGateway;
import com.fiap.techChallenge.application.useCases.NotificationUseCaseImpl;
import com.fiap.techChallenge.domain.services.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private EmailNotificationGateway emailNotificationGateway;

    @InjectMocks
    private NotificationUseCaseImpl notificationService;

    private final String toEmail = "cliente@exemplo.com";
    private final UUID orderId = UUID.randomUUID();
    private final String status = "RECEBIDO";

    @Test
    void shouldCallGatewayWhenNotifyingStatus() {
        // Act
        notificationService.notifyStatus(toEmail, orderId, status);

        // Assert
        verify(emailNotificationGateway).sendEmail(toEmail, orderId, status);
    }

    @Test
    void shouldImplementNotificationServiceInterface() {
        // Assert
        assertTrue(notificationService instanceof NotificationService,
                "NotificationUseCaseImpl deve implementar NotificationService");
    }
}