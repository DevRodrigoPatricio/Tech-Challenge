package com.fiap.techChallenge.application.services;

import com.fiap.techChallenge.application.ports.PaymentProcessingPort;
import com.fiap.techChallenge.domain.PaymentRequest;
import com.fiap.techChallenge.domain.PaymentResponse;
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
