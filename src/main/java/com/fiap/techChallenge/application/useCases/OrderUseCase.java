package com.fiap.techChallenge.application.useCases;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.dto.OrderWithItemsAndStatusDTO;
import com.fiap.techChallenge.domain.order.projection.OrderWithStatusAndWaitMinutesProjection;
import com.fiap.techChallenge.domain.order.projection.OrderWithStatusProjection;
import com.fiap.techChallenge.domain.order.request.OrderRequest;

public interface OrderUseCase {

    Order save(OrderRequest order);

    Order addItem(UUID id, UUID productId, int quantity);

    Order removeItem(UUID id, UUID productId, int quantity);

    OrderWithItemsAndStatusDTO findById(UUID id);

    List<OrderWithStatusProjection> listByPeriod(LocalDateTime initialDt, LocalDateTime finalDt);

    List<OrderWithStatusAndWaitMinutesProjection> listTodayOrders();

}
