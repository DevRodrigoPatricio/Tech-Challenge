package com.fiap.techChallenge.application.ports;

import com.fiap.techChallenge.domain.PaymentRequest;
import com.fiap.techChallenge.domain.PaymentResponse;

public interface PaymentProcessingPort {
    PaymentResponse processPayment(PaymentRequest request);
}
