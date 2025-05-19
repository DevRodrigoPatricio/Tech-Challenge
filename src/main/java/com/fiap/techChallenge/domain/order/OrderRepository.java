package com.fiap.techChallenge.domain.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(UUID id);

    Order validate(UUID id);

    List<Order> listByClient(UUID customerId);

    List<Order> listByPeriod(LocalDateTime initialDt, LocalDateTime finalDt);

}
