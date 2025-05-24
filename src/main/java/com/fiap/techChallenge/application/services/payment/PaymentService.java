package com.fiap.techChallenge.application.services.payment;

import com.fiap.techChallenge.adapters.outbound.storage.payment.PaymentProcessingPort;
import com.fiap.techChallenge.application.useCases.order.OrderUseCase;
import com.fiap.techChallenge.application.useCases.payment.ProcessPaymentUseCase;
import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.domain.enums.PaymentStatus;
import com.fiap.techChallenge.application.dto.payment.PaymentRequestDTO;
import com.fiap.techChallenge.application.dto.payment.PaymentResponseDTO;

import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistory;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService implements ProcessPaymentUseCase {

    private final PaymentProcessingPort paymentProcessor;
    private final OrderUseCase orderUseCase;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;

    public PaymentService(
            PaymentProcessingPort paymentProcessor,
            OrderUseCase orderUseCase,
            OrderStatusHistoryRepository orderStatusHistoryRepository
    ) {
        this.paymentProcessor = paymentProcessor;
        this.orderUseCase = orderUseCase;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
    }

    public PaymentResponseDTO process(PaymentRequestDTO request) {
        Order order = orderUseCase.validate(request.getOrderId());

        return paymentProcessor.processPayment(request, order);
    }

    public PaymentStatus processPayment(UUID orderId) {
        PaymentStatus paymentStatus = paymentProcessor.checkStatus(orderId);
        OrderStatus status;

        switch (paymentStatus) {
            case APPROVED -> status = OrderStatus.RECEBIDO;
            case REJECTED, EXPIRED -> status = OrderStatus.NAO_PAGO;
            default -> status = OrderStatus.PAGAMENTO_PENDENTE;
        }

        OrderStatusHistory history = new OrderStatusHistory(
                orderId,
                status,
                LocalDateTime.now()
        );

        if (history.getStatus() != OrderStatus.PAGAMENTO_PENDENTE) {
            orderStatusHistoryRepository.save(history);
        }

        return paymentStatus;
    }
}
