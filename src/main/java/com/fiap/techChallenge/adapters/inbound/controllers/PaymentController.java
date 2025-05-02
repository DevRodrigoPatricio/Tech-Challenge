package com.fiap.techChallenge.adapters.inbound.controller;

import com.fiap.techChallenge.application.usecases.ProcessPaymentUseCase;
import com.fiap.techChallenge.domain.PaymentRequest;
import com.fiap.techChallenge.domain.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagamento")
public class PaymentController {

    private final ProcessPaymentUseCase processPaymentUseCase;

    public PaymentController(ProcessPaymentUseCase processPaymentUseCase) {
        this.processPaymentUseCase = processPaymentUseCase;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest request) {
        PaymentResponse response = processPaymentUseCase.execute(request);
        return ResponseEntity.ok(response);
    }


}
