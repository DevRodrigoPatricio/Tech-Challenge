package com.fiap.techChallenge.application.services.payment;

import com.fiap.techChallenge.adapters.outbound.storage.payment.PaymentProcessingPort;
import com.fiap.techChallenge.application.useCases.payment.ProcessPaymentUseCase;
import com.fiap.techChallenge.domain.enums.PaymentStatus;
import com.fiap.techChallenge.application.dto.payment.PaymentRequestDTO;
import com.fiap.techChallenge.application.dto.payment.PaymentResponseDTO;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService implements ProcessPaymentUseCase {

    private final PaymentProcessingPort paymentProcessor;

    public PaymentService(PaymentProcessingPort paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public PaymentResponseDTO process(PaymentRequestDTO request) {
        return paymentProcessor.processPayment(request);
    }


    public PaymentStatus processPayment(UUID orderId) {
        return paymentProcessor.checkStatus(orderId);
    }
}
