package com.fiap.techChallenge.adapters.inbound.controllers.payments;

import com.fiap.techChallenge.application.useCases.payment.ProcessPaymentUseCase;
import com.fiap.techChallenge.domain.enums.PaymentStatus;
import com.fiap.techChallenge.application.dto.payment.PaymentRequestDTO;
import com.fiap.techChallenge.application.dto.payment.PaymentResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/pagamento")
@Tag(name = "Pagamento de Pedidos", description = "Endpoints para gerenciamento de pagamentos dos pedidos.")
public class PaymentController {

    private final ProcessPaymentUseCase processPaymentUseCase;

    public PaymentController(ProcessPaymentUseCase processPaymentUseCase) {
        this.processPaymentUseCase = processPaymentUseCase;
    }

    @PostMapping
    @Operation(summary = "criar pagamento por qrCode.",
            description = "Criação do qrCode para fazer o pagamento do pedido")
    public ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody PaymentRequestDTO request) {
        PaymentResponseDTO response = processPaymentUseCase.process(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{orderId}")
    @Operation(summary = "Consultar status de pagamento do pedido.",
            description = "retorna o  status de pagamento do pedido.")
    public ResponseEntity<PaymentStatus> getPaymentStatus(@PathVariable UUID orderId) {
        return ResponseEntity.ok(processPaymentUseCase.processPayment(orderId));
    }
}
