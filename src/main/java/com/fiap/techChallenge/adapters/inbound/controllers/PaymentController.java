package com.fiap.techChallenge.adapters.inbound.controllers;

import com.fiap.techChallenge.application.useCases.ProcessPaymentUseCase;
import com.fiap.techChallenge.domain.PaymentRequest;
import com.fiap.techChallenge.domain.PaymentResponse;
import com.fiap.techChallenge.domain.enums.PaymentStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("{orderId}")
    public ResponseEntity<PaymentStatus> getPaymentStatus(@PathVariable UUID orderId) {
        return ResponseEntity.ok(processPaymentUseCase.processPayment(orderId));
    }
}
