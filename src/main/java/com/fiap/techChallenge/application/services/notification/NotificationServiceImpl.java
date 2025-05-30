package com.fiap.techChallenge.application.services.notification;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fiap.techChallenge.application.useCases.notification.NotificationStatusUseCase;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationStatusUseCase {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    @Override
    public void notifyStatus(String toEmail, UUID orderId, String status) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(toEmail);
            helper.setSubject("Atualização do Pedido #" + orderId);

            String htmlContent = buildHtmlContent(orderId, status);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }

    private String buildHtmlContent(UUID orderId, String status) {
        return """
            <html>
              <body style="font-family: Arial, sans-serif; color: #333;">
                <h2>Olá!</h2>
                <p>Seu pedido <strong>#%s</strong> está agora com o status: <strong>%s</strong>.</p>
                <p>Agradecemos pela sua preferência.</p>
                <br/>
                <p>Atenciosamente,<br/>Equipe Challenge</p>
              </body>
            </html>
            """.formatted(orderId, status);
    }
}
