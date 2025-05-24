package com.fiap.techChallenge.application.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import java.util.UUID;

import com.fiap.techChallenge.application.useCases.OrderStatusHistoryUseCase;
import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderRepository;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistory;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRepository;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRequest;
import com.fiap.techChallenge.domain.user.attendant.Attendant;
import com.fiap.techChallenge.domain.user.attendant.AttendantRepository;
import com.fiap.techChallenge.utils.exceptions.EntityNotFoundException;
import com.fiap.techChallenge.utils.exceptions.InvalidOrderStatusException;

@Service
public class OrderStatusHistoryServiceImpl implements OrderStatusHistoryUseCase {

    private final OrderStatusHistoryRepository repository;
    private final OrderRepository orderRepository;
    private final AttendantRepository attendantRepository;

    public OrderStatusHistoryServiceImpl(OrderStatusHistoryRepository repository, OrderRepository orderRepository, AttendantRepository attendantRepository) {
        this.repository = repository;
        this.orderRepository = orderRepository;
        this.attendantRepository = attendantRepository;
    }

    @Override
    public OrderStatusHistory save(OrderStatusHistoryRequest request) {
        this.isValidStatusTransition(request.getOrderId(), request.getStatus());

        OrderStatusHistory orderStatusHistory = new OrderStatusHistory();
        orderStatusHistory.setId(null);
        orderStatusHistory.setOrderId(request.getOrderId());
        orderStatusHistory.setStatus(request.getStatus());
        orderStatusHistory.setDate(LocalDateTime.now());
        OrderStatusHistory result = repository.save(orderStatusHistory);

        Order order = orderRepository.validate(request.getOrderId());
        Attendant attendant = attendantRepository.findById(request.getAttendantId()).orElseThrow(() -> new EntityNotFoundException("Atendente"));
        order.setAttendant(attendant);
        orderRepository.save(order);

        return result;
    }

    @Override
    public List<OrderStatusHistory> list(UUID orderId) {
        return repository.list(orderId);
    }

    @Override
    public OrderStatusHistory findById(UUID orderStatusHistoryId) {
        return repository.findById(orderStatusHistoryId).orElse(OrderStatusHistory.empty());
    }

    @Override
    public OrderStatusHistory findLast(UUID orderId) {
        return repository.findLast(orderId).orElse(OrderStatusHistory.empty());
    }

    public boolean validateIfStatusAlreadyExists(UUID orderId, OrderStatus status) {

        if (repository.existsByOrderIdAndStatus(orderId, status)) {
            throw new IllegalArgumentException("Status já registrado para esse Pedido");

        } else {
            return false;
        }
    }

    public void isValidStatusTransition(UUID orderId, OrderStatus newStatus) {
        validateIfStatusAlreadyExists(orderId, newStatus);

        final String historyNotFound = "Histórico de status não encontrado para o pedido informado";
        OrderStatusHistory lastStatus;
        OrderStatus requiredStatus;

        switch (newStatus) {
            case PAGAMENTO_PENDENTE -> {
                lastStatus = findLastStatusOrThrow(orderId, historyNotFound);
                requiredStatus = OrderStatus.RECEBIDO;
                validateStatus(newStatus, lastStatus.getStatus(), requiredStatus);
            }

            case NAO_PAGO -> {
                lastStatus = findLastStatusOrThrow(orderId, historyNotFound);
                requiredStatus = OrderStatus.PAGAMENTO_PENDENTE;
                validateStatus(newStatus, lastStatus.getStatus(), requiredStatus);
            }

            case PAGO -> {
                lastStatus = findLastStatusOrThrow(orderId, historyNotFound);
                validateStatus(newStatus, lastStatus.getStatus(),
                        OrderStatus.RECEBIDO, OrderStatus.PAGAMENTO_PENDENTE, OrderStatus.NAO_PAGO);
            }

            case RECEBIDO -> {
                lastStatus = findLastStatusOrThrow(orderId, historyNotFound);
                validateStatus(newStatus, lastStatus.getStatus(), OrderStatus.PAGO);
            }

            case EM_PREPARACAO -> {
                lastStatus = findLastStatusOrThrow(orderId, historyNotFound);
                requiredStatus = OrderStatus.RECEBIDO;
                validateStatus(newStatus, lastStatus.getStatus(), requiredStatus);
            }

            case PRONTO -> {
                lastStatus = findLastStatusOrThrow(orderId, historyNotFound);
                requiredStatus = OrderStatus.EM_PREPARACAO;
                validateStatus(newStatus, lastStatus.getStatus(), requiredStatus);
            }

            case FINALIZADO -> {
                lastStatus = findLastStatusOrThrow(orderId, historyNotFound);
                requiredStatus = OrderStatus.PRONTO;
                validateStatus(newStatus, lastStatus.getStatus(), requiredStatus);
            }

            case CANCELADO ->
                findLastStatusOrThrow(orderId, historyNotFound);
        }
    }

    private OrderStatusHistory findLastStatusOrThrow(UUID orderId, String message) {
        OrderStatusHistory lastStatus = this.findLast(orderId);

        if (lastStatus.getId() == null) {
            throw new EntityNotFoundException(message);
        }

        return lastStatus;
    }

    private void validateStatus(OrderStatus newStatus, OrderStatus currentStatus, OrderStatus... requiredStatusList) {
        for (OrderStatus required : requiredStatusList) {
            if (currentStatus == required) {
                return;
            }
        }
        throw new InvalidOrderStatusException(newStatus, currentStatus, requiredStatusList);
    }

}
