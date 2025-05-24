package com.fiap.techChallenge.application.useCases.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fiap.techChallenge.application.dto.order.OrderWithItemsAndStatusDTO;
import com.fiap.techChallenge.application.dto.order.projection.OrderWithStatusAndWaitMinutesProjection;
import com.fiap.techChallenge.application.dto.order.projection.OrderWithStatusProjection;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.application.dto.order.request.OrderRequestDTO;

public interface OrderUseCase {

    Order save(OrderRequestDTO order);

    Order addItem(UUID id, UUID productId, int quantity);

    Order removeItem(UUID id, UUID productId, int quantity);

    OrderWithItemsAndStatusDTO findById(UUID id);

    List<OrderWithStatusProjection> listByPeriod(LocalDateTime initialDt, LocalDateTime finalDt);

    List<OrderWithStatusAndWaitMinutesProjection> listTodayOrders();

    Order validate(UUID id);
}
