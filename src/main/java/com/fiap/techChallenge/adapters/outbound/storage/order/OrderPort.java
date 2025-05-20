package com.fiap.techChallenge.adapters.outbound.storage.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderRequest;

public interface OrderPort {

    Order save(OrderRequest order);

    Order addItem(UUID id, UUID productId, int quantity);

    Order removeItem(UUID id, UUID productId, int quantity);

    Optional<Order> findById(UUID id);

    List<Order> listByClient(UUID customerId);

    List<Order> listByPeriod(LocalDateTime initialDt, LocalDateTime finalDt);

    void delete(UUID id);
}
