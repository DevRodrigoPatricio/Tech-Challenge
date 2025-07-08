package com.fiap.techChallenge;

import com.fiap.techChallenge.infrastructure.gateways.EmailNotificationGatewayImpl;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailNotificationGatewayImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailNotificationGatewayImpl emailNotificationGateway;

    private final String fromEmail = "no-reply@techchallenge.com.br";
    private final String toEmail = "cliente@exemplo.com";
    private final UUID orderId = UUID.randomUUID();
    private final String status = "RECEBIDO";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(emailNotificationGateway, "from", fromEmail);
    }

    @Test
    void shouldSendEmailWithCorrectParameters() throws Exception {
        // Arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Act
        emailNotificationGateway.sendEmail(toEmail, orderId, status);

        // Assert
        verify(mailSender).createMimeMessage();
        verify(mailSender).send(any(MimeMessage.class));
    }

    @Test
    void shouldBuildCorrectHtmlContent() {
        // Act
        String htmlContent = emailNotificationGateway.buildHtmlContent(orderId, status);

        // Assert
        assertAll(
                () -> assertTrue(htmlContent.contains("<h2>Olá!</h2>"), "Apresentação não encontrada"),
                () -> assertTrue(htmlContent.contains("Seu pedido <strong>#" + orderId + "</strong>"),
                        "ID do pedido não encontrado"),
                () -> assertTrue(htmlContent.contains("status: <strong>" + status + "</strong>"),
                        "Status não encontrado"),
                () -> assertTrue(htmlContent.contains("Atenciosamente,<br/>Equipe Challenge"),
                        "Rodapé não encontrado"),
                () -> assertTrue(htmlContent.contains("<html>"), "Deve conter tag HTML de abertura"),
                () -> assertTrue(htmlContent.contains("</html>"), "Deve conter tag HTML de fechamento")
        );
    }

    @Test
    void shouldIncludeAllStatusOptionsInTemplate() {
        String[] allStatuses = {
                "PAGAMENTO_PENDENTE",
                "NAO_PAGO",
                "RECEBIDO",
                "EM_PREPARACAO",
                "PRONTO",
                "FINALIZADO",
                "CANCELADO"
        };

        for (String statusValue : allStatuses) {
            String htmlContent = emailNotificationGateway.buildHtmlContent(orderId, statusValue);
            assertTrue(htmlContent.contains(statusValue),
                    "Deveria suportar o status: " + statusValue);
        }
    }
}