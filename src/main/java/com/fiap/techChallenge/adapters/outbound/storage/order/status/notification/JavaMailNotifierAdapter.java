package com.fiap.techChallenge.adapters.outbound.storage.order.status.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JavaMailNotifierAdapter implements NotifierPort {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    @Override
    public void sendStatus(String toEmail, UUID orderId, String status) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(toEmail);
            helper.setSubject("Atualização do Pedido #" + orderId);

            String htmlContent = buildHtmlContent(orderId, status);
            helper.setText(htmlContent, true); // HTML format

            mailSender.send(message);
        } catch (MessagingException e) {
            // Em produção: logue isso com logger, não use printStackTrace
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
            e.printStackTrace();
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
                <p>Atenciosamente,<br/>Equipe Tech Challenge</p>
              </body>
            </html>
            """.formatted(orderId, status);
    }
}
