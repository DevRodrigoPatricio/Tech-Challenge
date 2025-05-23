package com.fiap.techChallenge.application.useCases.order;

import com.fiap.techChallenge.domain.order.status.OrderStatusHistory;
import com.fiap.techChallenge.application.dto.order.OrderStatusHistoryRequestDTO;
import com.fiap.techChallenge.application.dto.order.OrderStatusWithClientAndWaitTimeDTO;

import java.util.List;
import java.util.UUID;

public interface OrderStatusHistoryUseCase {

    OrderStatusHistory save(OrderStatusHistoryRequestDTO request);

    OrderStatusHistory findById(UUID id);

    List<OrderStatusHistory> list(UUID orderId);

    List<OrderStatusWithClientAndWaitTimeDTO> listTodayOrderStatus();

    OrderStatusHistory findLast(UUID orderId);

}
