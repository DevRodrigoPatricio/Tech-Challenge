package com.fiap.techChallenge.application.dto.order.projection;

import java.math.BigDecimal;
import java.util.UUID;

import com.fiap.techChallenge.domain.enums.Category;

public interface OrderItemProjection {

    UUID getProductId();

    String getProductName();

    Category getCategory();

    int getQuantity();

    BigDecimal getUnitPrice();

}
