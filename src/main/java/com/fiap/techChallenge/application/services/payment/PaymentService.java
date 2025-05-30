package com.fiap.techChallenge.application.services.payment;

import com.fiap.techChallenge.adapters.outbound.storage.payment.PaymentProcessingPort;
import com.fiap.techChallenge.domain.core.order.Order;
import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.domain.enums.PaymentStatus;
import com.fiap.techChallenge.application.useCases.order.OrderUseCase;
import com.fiap.techChallenge.application.useCases.payment.ProcessPaymentUseCase;
import com.fiap.techChallenge.application.dto.order.UpdateOrderStatusHistoryDTO;
import com.fiap.techChallenge.application.dto.payment.PaymentRequestDTO;
import com.fiap.techChallenge.application.dto.payment.PaymentResponseDTO;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService implements ProcessPaymentUseCase {

    private final PaymentProcessingPort paymentProcessor;
    private final OrderUseCase orderUseCase;

    public PaymentService(
            PaymentProcessingPort paymentProcessor,
            OrderUseCase orderUseCase
    ) {
        this.paymentProcessor = paymentProcessor;
        this.orderUseCase = orderUseCase;
    }

    @Override
    public PaymentResponseDTO process(PaymentRequestDTO request) {
        Order order = orderUseCase.validate(request.getOrderId());

        return paymentProcessor.processPayment(request, order);
    }

    @Override
    public PaymentStatus processPayment(UUID orderId) {
        PaymentStatus paymentStatus = paymentProcessor.checkStatus(orderId);
        OrderStatus status;

        switch (paymentStatus) {
            case APPROVED ->
                status = OrderStatus.RECEBIDO;
            case REJECTED, EXPIRED ->
                status = OrderStatus.NAO_PAGO;
            default ->
                status = OrderStatus.PAGAMENTO_PENDENTE;
        }

        UpdateOrderStatusHistoryDTO orderStatus = new UpdateOrderStatusHistoryDTO(
                orderId,
                null,
                status
        );

        if (orderStatus.getStatus() != OrderStatus.PAGAMENTO_PENDENTE) {
            orderUseCase.updateStatus(orderStatus);
            Order order = orderUseCase.validate(orderId);
            order.setDate(LocalDateTime.now());
            orderUseCase.save(order);
        }

        return paymentStatus;
    }
}
