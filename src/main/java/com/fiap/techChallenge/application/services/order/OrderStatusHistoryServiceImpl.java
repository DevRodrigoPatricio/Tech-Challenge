package com.fiap.techChallenge.application.services.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import java.util.UUID;

import com.fiap.techChallenge.application.useCases.order.OrderStatusHistoryUseCase;
import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderRepository;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistory;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRepository;
import com.fiap.techChallenge.application.dto.order.OrderStatusHistoryRequestDTO;
import com.fiap.techChallenge.application.dto.order.OrderStatusWithClientAndWaitTimeDTO;
import com.fiap.techChallenge.domain.exceptions.EntityNotFoundException;
import com.fiap.techChallenge.domain.exceptions.order.InvalidOrderStatusException;

@Service
public class OrderStatusHistoryServiceImpl implements OrderStatusHistoryUseCase {

    private final OrderStatusHistoryRepository repository;
    private final OrderRepository orderRepository;

    public OrderStatusHistoryServiceImpl(OrderStatusHistoryRepository repository, OrderRepository orderRepository) {
        this.repository = repository;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderStatusHistory save(OrderStatusHistoryRequestDTO request) {
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
    public OrderStatusHistory findById(UUID orderStatusHistoryId) {
        return repository.findById(orderStatusHistoryId).orElse(OrderStatusHistory.empty());
    }

    @Override
    public List<OrderStatusWithClientAndWaitTimeDTO> listTodayOrderStatus() {
        List<String> statusList = List.of(
                OrderStatus.RECEBIDO.name(),
                OrderStatus.EM_PREPARACAO.name(),
                OrderStatus.PRONTO.name(),
                OrderStatus.FINALIZADO.name()
        );

        return repository.listTodayOrderStatus(statusList, 5);
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
        OrderStatusHistory lastStatus = this.findLast(orderId);

        if(lastStatus.getId() == null) {
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
