package com.fiap.techChallenge.application.ports;

import com.fiap.techChallenge.domain.enums.PaymentStatus;

public interface PaymentStatusPort {
    PaymentStatus checkStatus(Long paymentId);
}

