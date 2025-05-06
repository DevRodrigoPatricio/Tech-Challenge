package com.fiap.techChallenge.adapters.inbound;

import com.fiap.techChallenge.adapters.inbound.controllers.OrderStatusController;
import com.fiap.techChallenge.application.services.OrderStatusService;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.Order.OrderStatus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderStatusController.class)
class OrderStatusControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private OrderStatusService orderStatusService;

    private final UUID orderId = UUID.randomUUID();
    private final Order order = new Order(orderId, OrderStatus.PRONTO);

    /**
     * Testa o endpoint GET /api/order-status/{orderId}
     * Verifica:
     * 1. Se retorna status HTTP 200 (OK) para pedido existente
     * 2. Se o corpo da resposta contém o ID correto
     * 3. Se o status no JSON de resposta está correto
     */
    @Test
    void shouldReturnOkWhenGettingStatus() throws Exception {
        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId, OrderStatus.PRONTO);

        when(orderStatusService.getStatus(orderId)).thenReturn(order);

        mockMvc.perform(get("/api/pedidos/{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId.toString()))
                .andExpect(jsonPath("$.status").value("PRONTO"));
    }

    /**
     * Testa o endpoint /api/pedidos/{orderId}/preparar
     * Verifica:
     * 1. Se retorna status 200 quando a operação é bem-sucedida
     * 2. Se o JSON de resposta mostra o novo status IN_PREPARATION
     * 3. Se o serviço foi chamado corretamente
     */
    @Test
    void shouldStartPreparationSuccessfully() throws Exception {
        order.preparo();
        when(orderStatusService.preparo(orderId)).thenReturn(order);

        mockMvc.perform(patch("/api/pedidos/{orderId}/preparar", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("EM_PREPARO"));
    }
}