package com.fiap.techChallenge.domain.exceptions.order;

import java.util.Arrays;
import java.util.Objects;

import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.domain.exceptions.DomainException;

public class InvalidOrderStatusException extends DomainException {

    public InvalidOrderStatusException(OrderStatus newStatus, OrderStatus currentStatus, OrderStatus... requiredStatusList) {
        super(buildMessage(newStatus, currentStatus, requiredStatusList));
    }

    public InvalidOrderStatusException(String message) {
        super(message);
    }

    private static String buildMessage(OrderStatus newStatus, OrderStatus currentStatus, OrderStatus... requiredStatusList) {
        Objects.requireNonNull(newStatus, "newStatus é obrigatório");
        Objects.requireNonNull(currentStatus, "currentStatus é obrigatório");
        Objects.requireNonNull(requiredStatusList, "requiredStatusList é obrigatório");

        return "O status do pedido não pode ser alterado para '" + newStatus
                + "', pois seu status atual é '" + currentStatus
                + "'. Essa transição requer que o pedido esteja em um dos seguintes status: '" + Arrays.toString(requiredStatusList) + "'";
    }
}
