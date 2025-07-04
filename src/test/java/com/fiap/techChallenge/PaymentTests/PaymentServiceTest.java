package com.fiap.techChallenge.PaymentTests;

import com.fiap.techChallenge.adapters.outbound.storage.payment.PaymentProcessingPort;
import com.fiap.techChallenge.application.dto.order.OrderDTO;
import com.fiap.techChallenge.application.dto.order.UpdateOrderStatusHistoryDTO;
import com.fiap.techChallenge.application.dto.payment.PaymentRequestDTO;
import com.fiap.techChallenge.application.dto.payment.PaymentResponseDTO;
import com.fiap.techChallenge.application.services.payment.PaymentServiceImpl;
import com.fiap.techChallenge.application.useCases.order.OrderUseCase;
import com.fiap.techChallenge.domain.core.order.Order;
import com.fiap.techChallenge.domain.enums.PaymentStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Mock
    private PaymentProcessingPort paymentProcessor;

    @Mock
    private OrderUseCase orderUseCase;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private UUID orderId;
    private Order mockOrder;
    private PaymentRequestDTO requestDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        orderId = UUID.randomUUID();

        mockOrder = new Order();
        mockOrder.setId(orderId);
        mockOrder.setPrice(BigDecimal.valueOf(50.00));
        mockOrder.setDate(LocalDateTime.now());

        requestDTO = new PaymentRequestDTO(orderId);
    }

    @Test
    public void testProcessPayment_shouldReturnResponseDTO() {
        PaymentResponseDTO mockResponse = new PaymentResponseDTO("PENDING", orderId, "qrcode_string");

        when(orderUseCase.validate(orderId)).thenReturn(mockOrder);
        when(paymentProcessor.processPayment(requestDTO, mockOrder)).thenReturn(mockResponse);

        PaymentResponseDTO response = paymentService.process(requestDTO);

        assertNotNull(response);
        assertEquals("PENDING", response.getStatus());
        assertEquals(orderId, response.getOrderId());
        assertEquals("qrcode_string", response.getQrCodeImage());

        verify(orderUseCase).validate(orderId);
        verify(paymentProcessor).processPayment(requestDTO, mockOrder);
    }

    @Test
    public void testCheckPaymentStatus_shouldReturnApprovedAndUpdateOrder() {
        when(paymentProcessor.checkStatus(orderId)).thenReturn(PaymentStatus.APPROVED);
        when(orderUseCase.validate(orderId)).thenReturn(mockOrder);

        PaymentStatus result = paymentService.processPayment(orderId);

        assertEquals(PaymentStatus.APPROVED, result);
        verify(orderUseCase).updateStatus(any(UpdateOrderStatusHistoryDTO.class));
        verify(orderUseCase).save(any(Order.class));
    }

    @Test
    public void testCheckPaymentStatus_shouldReturnPendingWithoutSaving() {
        when(paymentProcessor.checkStatus(orderId)).thenReturn(PaymentStatus.PENDING);

        PaymentStatus result = paymentService.processPayment(orderId);

        assertEquals(PaymentStatus.PENDING, result);
        verify(orderUseCase, never()).updateStatus(any());
        verify(orderUseCase, never()).save((OrderDTO) any());
    }

    @Test
    public void testCheckPaymentStatus_shouldReturnRejectedAndUpdateOrder() {
        when(paymentProcessor.checkStatus(orderId)).thenReturn(PaymentStatus.REJECTED);
        when(orderUseCase.validate(orderId)).thenReturn(mockOrder);

        PaymentStatus result = paymentService.processPayment(orderId);

        assertEquals(PaymentStatus.REJECTED, result);
        verify(orderUseCase).updateStatus(any(UpdateOrderStatusHistoryDTO.class));
        verify(orderUseCase).save(any(Order.class));
    }
}