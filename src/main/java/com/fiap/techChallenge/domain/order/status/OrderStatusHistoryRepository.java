package com.fiap.techChallenge.domain.order.status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.OrderStatus;

public interface OrderStatusHistoryRepository {

    Optional<OrderStatusHistory> findById(UUID orderStatusHistoryId);

    OrderStatusHistory save(OrderStatusHistory orderStatusHistory);

    List<OrderStatusHistory> list(UUID orderId);

    Optional<OrderStatusHistory> findLast(UUID orderId);

    boolean existsByOrderIdAndStatus(UUID orderId, OrderStatus status);

    List<OrderStatusWithClientAndWaitTimeDTO> listTodayOrderStatus(List<String> statusList, int finalizedMinutes);

}
