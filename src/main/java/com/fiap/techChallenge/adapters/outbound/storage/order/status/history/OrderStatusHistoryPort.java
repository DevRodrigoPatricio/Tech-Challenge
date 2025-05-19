package com.fiap.techChallenge.adapters.outbound.storage.order.status.history;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fiap.techChallenge.domain.order.status.OrderStatusHistory;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRequest;
import com.fiap.techChallenge.domain.order.status.OrderStatusWithClientAndWaitTimeDTO;

public interface OrderStatusHistoryPort {

    OrderStatusHistory save(OrderStatusHistoryRequest request);

    Optional<OrderStatusHistory> findById(UUID orderStatusHistoryId);

    List<OrderStatusHistory> list(UUID orderId);

    Optional<OrderStatusHistory> findLast(UUID orderId);

    List<OrderStatusWithClientAndWaitTimeDTO> listTodayOrderStatus();
}
