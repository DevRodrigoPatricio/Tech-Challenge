package com.fiap.techChallenge.application.useCases.payment;

import com.fiap.techChallenge.domain.enums.PaymentStatus;
import com.fiap.techChallenge.application.dto.payment.PaymentRequestDTO;
import com.fiap.techChallenge.application.dto.payment.PaymentResponseDTO;
import java.util.UUID;

public interface  ProcessPaymentUseCase {

     PaymentResponseDTO process(PaymentRequestDTO request) ;
     PaymentStatus processPayment(UUID orderId) ;

}