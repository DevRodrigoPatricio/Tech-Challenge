package com.fiap.techChallenge.adapters.outbound.storage.order.status.history;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderRepository;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistory;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRepository;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRequest;
import com.fiap.techChallenge.utils.exceptions.EntityNotFoundException;
import com.fiap.techChallenge.utils.exceptions.InvalidOrderStatusException;

@Component
public class OrderStatusHistoryAdapter implements OrderStatusHistoryPort {

    private final OrderStatusHistoryRepository repository;
    private final OrderRepository orderRepository;

    public OrderStatusHistoryAdapter(OrderStatusHistoryRepository repository, OrderRepository orderRepository) {
        this.repository = repository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<OrderStatusHistory> findById(UUID orderStatusHistoryId) {
        return repository.findById(orderStatusHistoryId);
    }

    @Override
    public OrderStatusHistory save(OrderStatusHistoryRequest request) {
        this.isValidStatusTransition(request.getOrderId(), request.getStatus());

        OrderStatusHistory orderStatusHistory = new OrderStatusHistory();
        orderStatusHistory.setId(null);
        orderStatusHistory.setOrderId(request.getOrderId());
        orderStatusHistory.setStatus(request.getStatus());
        orderStatusHistory.setDate(LocalDateTime.now());
        return repository.save(orderStatusHistory);
    }

    @Override
    public List<OrderStatusHistory> list(UUID orderId) {
        return repository.list(orderId);
    }

    @Override
    public Optional<OrderStatusHistory> findLast(UUID orderId) {
        return repository.findLast(orderId);
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

        if (orderId == null && newStatus != OrderStatus.INICIADO) {
            throw new IllegalArgumentException("orderId é obrigatório");
        }

        final String historyNotFound = "Histórico de status não encontrado para o pedido informado";
        OrderStatusHistory lastStatus;
        OrderStatus requiredStatus;

        switch (newStatus) {
            case INICIADO -> {
                if (orderId != null) {
                    if (!this.list(orderId).isEmpty()) {
                        throw new InvalidOrderStatusException("O pedido não pode ser iniciado, pois já foi cadastrado anteriormente");
                    }
                }
            }

            case CONFIRMADO -> {
                lastStatus = findLastStatusOrThrow(orderId, historyNotFound);
                requiredStatus = OrderStatus.INICIADO;
                validateStatus(newStatus, lastStatus.getStatus(), requiredStatus);
                Order order = orderRepository.validate(orderId);

                if (order.getItems().isEmpty()) {
                    throw new IllegalArgumentException("O pedido não pode ser confirmado, pois não possui produtos cadastrados");
                }
            }

            case PAGAMENTO_PENDENTE -> {
                lastStatus = findLastStatusOrThrow(orderId, historyNotFound);
                requiredStatus = OrderStatus.CONFIRMADO;
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
                        OrderStatus.RECEBIDO, OrderStatus.CONFIRMADO,
                        OrderStatus.PAGAMENTO_PENDENTE, OrderStatus.NAO_PAGO);
            }

            case RECEBIDO -> {
                lastStatus = findLastStatusOrThrow(orderId, historyNotFound);
                validateStatus(newStatus, lastStatus.getStatus(),
                        OrderStatus.CONFIRMADO, OrderStatus.PAGO);
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
        return this.findLast(orderId).orElseThrow(() -> new EntityNotFoundException(message));
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
