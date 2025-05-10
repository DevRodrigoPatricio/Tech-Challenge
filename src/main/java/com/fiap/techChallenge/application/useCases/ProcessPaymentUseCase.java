package com.fiap.techChallenge.application.useCases;

import com.fiap.techChallenge.domain.enums.PaymentStatus;
import com.fiap.techChallenge.domain.payment.PaymentRequest;
import com.fiap.techChallenge.domain.payment.PaymentResponse;
import java.util.UUID;

public interface  ProcessPaymentUseCase {

     PaymentResponse process(PaymentRequest request) ;
     PaymentStatus processPayment(UUID orderId) ;

}