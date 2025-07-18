package com.fiap.techChallenge.adapters.outbound.storage.payment;

import com.fiap.techChallenge.domain.core.order.Order;
import com.fiap.techChallenge.domain.enums.PaymentStatus;
import com.fiap.techChallenge.application.dto.payment.PaymentRequestDTO;
import com.fiap.techChallenge.application.dto.payment.PaymentResponseDTO;

import java.util.UUID;

public interface PaymentProcessingPort {

    PaymentResponseDTO processPayment(PaymentRequestDTO request, Order order);

    PaymentStatus checkStatus(UUID paymentId);
}
