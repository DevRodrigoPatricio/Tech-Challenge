package com.fiap.techChallenge.adapters.outbound.storage.payment;

import com.fiap.techChallenge.domain.enums.PaymentStatus;
import com.fiap.techChallenge.domain.payment.PaymentRequest;
import com.fiap.techChallenge.domain.payment.PaymentResponse;

import java.util.UUID;

public interface PaymentProcessingPort {

    PaymentResponse processPayment(PaymentRequest request);

    PaymentStatus checkStatus(UUID paymentId);
}
