package com.fiap.techChallenge.application.ports;

import com.fiap.techChallenge.domain.PaymentRequest;
import com.fiap.techChallenge.domain.PaymentResponse;
import com.fiap.techChallenge.domain.enums.PaymentStatus;
import java.util.UUID;

public interface PaymentProcessingPort {
    PaymentResponse processPayment(PaymentRequest request);
    PaymentStatus checkStatus(UUID paymentId);
}
