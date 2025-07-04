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
public class PaymentServiceImpl implements ProcessPaymentUseCase {

    private final PaymentProcessingPort paymentProcessor;
    private final OrderUseCase orderUseCase;

    public PaymentServiceImpl(
            PaymentProcessingPort paymentProcessor,
            OrderUseCase orderUseCase
    ) {
        this.paymentProcessor = paymentProcessor;
        this.orderUseCase = orderUseCase;
    }

    @Override
    public PaymentResponseDTO process(PaymentRequestDTO request) {

        Order order = orderUseCase.validate(request.getOrderId());
        if (order == null) {
            throw new IllegalArgumentException("Order não encontrada ou inválida.");
        }

        return paymentProcessor.processPayment(request, order);
    }

    @Override
    public PaymentStatus processPayment(UUID orderId) {
        PaymentStatus paymentStatus = paymentProcessor.checkStatus(orderId);
        OrderStatus status = mapPaymentStatusToOrderStatus(paymentStatus);

        if (status != OrderStatus.PAGAMENTO_PENDENTE) {
            updateOrderStatus(orderId, status);
        }

        return paymentStatus;
    }

    private OrderStatus mapPaymentStatusToOrderStatus(PaymentStatus paymentStatus) {
        switch (paymentStatus) {
            case APPROVED:
                return OrderStatus.RECEBIDO;
            case REJECTED:
            case EXPIRED:
                return OrderStatus.NAO_PAGO;
            default:
                return OrderStatus.PAGAMENTO_PENDENTE;
        }
    }

    private void updateOrderStatus(UUID orderId, OrderStatus status) {
        UpdateOrderStatusHistoryDTO orderStatus = new UpdateOrderStatusHistoryDTO(orderId, null, status);
        orderUseCase.updateStatus(orderStatus);


        Order order = orderUseCase.validate(orderId);
        if (order != null) {
            order.setDate(LocalDateTime.now());
            orderUseCase.save(order);
        } else {
            throw new IllegalArgumentException("Order não encontrada ou inválida para update de status.");
        }
    }
}
