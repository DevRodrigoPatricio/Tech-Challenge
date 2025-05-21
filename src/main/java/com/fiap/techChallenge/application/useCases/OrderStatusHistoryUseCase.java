package com.fiap.techChallenge.application.useCases;

import com.fiap.techChallenge.domain.order.status.OrderStatusHistory;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRequest;
import com.fiap.techChallenge.domain.order.status.OrderStatusWithClientAndWaitTimeDTO;

import java.util.List;
import java.util.UUID;

public interface OrderStatusHistoryUseCase {

    OrderStatusHistory save(OrderStatusHistoryRequest request);

    OrderStatusHistory findById(UUID id);

    List<OrderStatusHistory> list(UUID orderId);

    List<OrderStatusWithClientAndWaitTimeDTO> listTodayOrderStatus();

    OrderStatusHistory findLast(UUID orderId);

}
