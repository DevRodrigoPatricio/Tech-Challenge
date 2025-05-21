package com.fiap.techChallenge.application.useCases;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderRequest;

public interface OrderUseCase {

    Order save(OrderRequest order);

    Order addItem(UUID id, UUID productId, int quantity);

    Order removeItem(UUID id, UUID productId, int quantity);

    Order findById(UUID id);

    List<Order> listByClient(UUID customerId);

    List<Order> listByPeriod(LocalDateTime initialDt, LocalDateTime finalDt);

    void delete(UUID id);
}
