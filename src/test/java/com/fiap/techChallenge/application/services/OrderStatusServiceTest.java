package com.fiap.techChallenge.application.services;

import com.fiap.techChallenge.adapters.outbound.storage.order.status.notification.OrderStatusNotificationPort;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.utils.exceptions.OrderNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderStatusServiceTest {

    // @Mock private OrderStatusRepository repository;
    // @Mock private OrderStatusNotificationPort notificationPort;
    // @InjectMocks private OrderStatusService service;

    // private final UUID orderId = UUID.randomUUID();
    // private final Order order = new Order(orderId, OrderStatus.RECEIVED);

    // /**
    //  * Testa o cenário de sucesso onde o preparo de um pedido é iniciado corretamente.
    //  * Verifica:
    //  * 1. Se o status do pedido é atualizado para IN_PREPARATION
    //  * 2. Se a notificação de mudança de status é disparada
    //  */
    // @Test
    // void shouldStartPreparationSuccessfully() {
    //     when(repository.findById(orderId)).thenReturn(Optional.of(order));
    //     when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    //     Order result = service.preparation(orderId);

    //     assertEquals(OrderStatus.IN_PREPARATION, result.getStatus());
    //     verify(notificationPort).notifyStatusChange(any());
    // }

    // /**
    //  * Testa o cenário onde um pedido não é encontrado no repositório.
    //  * Verifica se a exceção OrderNotFoundException é lançada.
    //  */
    // @Test
    // void shouldThrowWhenOrderNotFound() {
    //     when(repository.findById(orderId)).thenReturn(Optional.empty());
    //     assertThrows(OrderNotFoundException.class, () -> service.preparation(orderId));
    // }

    // /**
    //  * Testa a recuperação do status de um pedido existente.
    //  * Verifica:
    //  * 1. Se o pedido retornado contém o ID correto
    //  * 2. Se o serviço consegue recuperar um pedido do repositório
    //  */
    // @Test
    // void shouldGetStatusSuccessfully() {
    //     when(repository.findById(orderId)).thenReturn(Optional.of(order));
    //     Order result = service.getStatus(orderId);
    //     assertEquals(orderId, result.getId());
    // }
}