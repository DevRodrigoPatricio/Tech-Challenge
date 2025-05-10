package com.fiap.techChallenge.domain;

import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.utils.exceptions.InvalidOrderStatusTransitionException;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    //Objetivo: Validar a lógica de negócio contida na classe Order (regras de transição de status).

    private final UUID orderId = UUID.randomUUID();
    private final Order order = new Order(orderId, Order.OrderStatus.RECEIVED);

    // O que testa: Verifica se o status muda para IN_PREPARATION e se o timestamp é definido.
    @Test
    void shouldStartPreparation() {
        order.preparation();
        assertEquals(Order.OrderStatus.IN_PREPARATION, order.getStatus());
        assertNotNull(order.getStartPreparation());
    }

    // O que testa: Garante que não se pode iniciar preparação novamente se já estiver em andamento.
    @Test
    void shouldNotStartPreparationFromInvalidStatus() {
        Order order = new Order(UUID.randomUUID(), Order.OrderStatus.IN_PREPARATION);

        assertThrows(InvalidOrderStatusTransitionException.class, () -> {
            order.preparation();
        });
    }

    // O que testa: Verificar se um pedido no status IN_PREPARATION (Em preparação) pode ser corretamente atualizado para READY (Pronto).
    @Test
    void shouldMarkAsReady() {
        order.preparation();
        order.ready();
        assertEquals(Order.OrderStatus.READY, order.getStatus());
        assertNotNull(order.getReadySchedule());
    }

    // O que testa: Garante que não se pode marcar como "pronto" se o status atual não for IN_PREPARATION.
    @Test
    void shouldNotMarkAsReadyFromInvalidStatus() {
        Order order = new Order(UUID.randomUUID(), Order.OrderStatus.RECEIVED);

        assertThrows(InvalidOrderStatusTransitionException.class, () -> {
            order.ready();
        });
    }

    // O que testa: Verificar se um pedido no status READY (Pronto) pode ser corretamente atualizado para DELIVERED (Entregue).
    @Test
    void shouldMarkAsDelivered() {
        order.preparation();
        order.ready();
        order.delivered();
        assertEquals(Order.OrderStatus.DELIVERED, order.getStatus());
        assertNotNull(order.getDeliveryTime());
    }

    // O que testa: Garante que não se pode marcar como "entregue" se o status atual não for READY
    @Test
    void shouldNotMarkAsDeliveredFromInvalidStatus() {
        Order order = new Order(UUID.randomUUID(), Order.OrderStatus.IN_PREPARATION);

        assertThrows(InvalidOrderStatusTransitionException.class, () -> {
            order.delivered();
        });
    }
}