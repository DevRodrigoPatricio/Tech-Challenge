package com.fiap.techChallenge.application.useCases;

import com.fiap.techChallenge.application.ports.PaymentProcessingPort;
import com.fiap.techChallenge.domain.PaymentRequest;
import com.fiap.techChallenge.domain.PaymentResponse;
import org.springframework.stereotype.Service;

@Service
public class ProcessPaymentUseCase {

    private final PaymentProcessingPort paymentProcessingPort;

    public ProcessPaymentUseCase(PaymentProcessingPort paymentProcessingPort) {
        this.paymentProcessingPort = paymentProcessingPort;
    }

    public PaymentResponse execute(PaymentRequest request) {
        return paymentProcessingPort.processPayment(request);
    }
}
