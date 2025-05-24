package com.fiap.techChallenge.domain.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fiap.techChallenge.domain.order.dto.OrderWithItemsAndStatusDTO;
import com.fiap.techChallenge.domain.order.projection.OrderWithStatusAndWaitMinutesProjection;
import com.fiap.techChallenge.domain.order.projection.OrderWithStatusProjection;

public interface OrderRepository {

    Order save(Order order);

    Optional<OrderWithItemsAndStatusDTO> findById(UUID id);

    Order validate(UUID id);

    List<OrderItem> findItemsById(UUID id);

    List<OrderWithStatusProjection> listByPeriod(LocalDateTime initialDt, LocalDateTime finalDt);

    List<OrderWithStatusAndWaitMinutesProjection> listTodayOrders(List<String> statusList, int finalizedMinutes);
}
