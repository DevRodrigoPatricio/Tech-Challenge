package com.fiap.techChallenge.domain;

import com.fiap.techChallenge.domain.exceptions.InvalidOrderStatusTransitionException;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    //Objetivo: Validar a lógica de negócio contida na classe Order (regras de transição de status).

    private final UUID orderId = UUID.randomUUID();
    private final Order order = new Order(orderId, Order.OrderStatus.RECEBIDO);

    // O que testa: Verifica se o status muda para IN_PREPARATION e se o timestamp é definido.
    @Test
    void shouldStartPreparation() {
        order.preparo();
        assertEquals(Order.OrderStatus.EM_PREPARACAO, order.getStatus());
        assertNotNull(order.getInicioPreparo());
    }

    // O que testa: Garante que não se pode iniciar preparação novamente se já estiver em andamento.
    @Test
    void shouldNotStartPreparationFromInvalidStatus() {
        Order order = new Order(UUID.randomUUID(), Order.OrderStatus.EM_PREPARACAO);

        assertThrows(InvalidOrderStatusTransitionException.class, () -> {
            order.preparo();
        });
    }

    // O que testa: Verificar se um pedido no status IN_PREPARATION (Em preparação) pode ser corretamente atualizado para READY (Pronto).
    @Test
    void shouldMarkAsReady() {
        order.preparo();
        order.pronto();
        assertEquals(Order.OrderStatus.PRONTO, order.getStatus());
        assertNotNull(order.getHorarioPronto());
    }

    // O que testa: Garante que não se pode marcar como "pronto" se o status atual não for IN_PREPARATION.
    @Test
    void shouldNotMarkAsReadyFromInvalidStatus() {
        Order order = new Order(UUID.randomUUID(), Order.OrderStatus.RECEBIDO);

        assertThrows(InvalidOrderStatusTransitionException.class, () -> {
            order.pronto();
        });
    }

    // O que testa: Verificar se um pedido no status READY (Pronto) pode ser corretamente atualizado para DELIVERED (Entregue).
    @Test
    void shouldMarkAsDelivered() {
        order.preparo();
        order.pronto();
        order.entregue();
        assertEquals(Order.OrderStatus.ENTREGUE, order.getStatus());
        assertNotNull(order.getHorarioEntregue());
    }

    // O que testa: Garante que não se pode marcar como "entregue" se o status atual não for READY
    @Test
    void shouldNotMarkAsDeliveredFromInvalidStatus() {
        Order order = new Order(UUID.randomUUID(), Order.OrderStatus.EM_PREPARACAO);

        assertThrows(InvalidOrderStatusTransitionException.class, () -> {
            order.entregue();
        });
    }
}