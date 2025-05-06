package com.fiap.techChallenge.application.services;

import com.fiap.techChallenge.adapters.outbound.storage.payment.PaymentProcessingPort;
import com.fiap.techChallenge.domain.payment.PaymentRequest;
import com.fiap.techChallenge.domain.payment.PaymentResponse;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentProcessingPort paymentProcessor;

    public PaymentService(PaymentProcessingPort paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public PaymentResponse process(PaymentRequest request) {
        return paymentProcessor.processPayment(request);
    }
}
