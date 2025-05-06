package com.fiap.techChallenge.application.useCases;

import com.fiap.techChallenge.adapters.outbound.storage.payment.PaymentProcessingPort;
import com.fiap.techChallenge.domain.enums.PaymentStatus;
import com.fiap.techChallenge.domain.payment.PaymentRequest;
import com.fiap.techChallenge.domain.payment.PaymentResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcessPaymentUseCase {

    private final PaymentProcessingPort paymentProcessor;

    public PaymentResponse execute(PaymentRequest request) {
        PaymentResponse response = paymentProcessor.processPayment(request);

        return response;
    }
    public PaymentStatus processPayment(UUID orderId) {
        return paymentProcessor.checkStatus(orderId);
    }

}