package com.fiap.techChallenge.application.services;

import com.fiap.techChallenge.application.ports.PaymentStatusPort;
import com.fiap.techChallenge.domain.enums.PaymentStatus;
import com.fiap.techChallenge.domain.exceptions.PaymentException;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MercadoPagoStatusService implements PaymentStatusPort {

    private final PaymentClient paymentClient = new PaymentClient();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    @Override
    public PaymentStatus checkStatus(Long paymentId) {
        try {
            Payment payment = paymentClient.get(paymentId);
            return mapStatus(payment.getStatus());
        } catch (Exception e) {
            throw new PaymentException("Failed to check payment status");
        }
    }

    private PaymentStatus mapStatus(String mpStatus) {
        return switch (mpStatus) {
            case "approved" -> PaymentStatus.APPROVED;
            case "rejected", "cancelled" -> PaymentStatus.REJECTED;
            default -> PaymentStatus.PENDING;
        };
    }
}