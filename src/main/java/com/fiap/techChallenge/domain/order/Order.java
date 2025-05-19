package com.fiap.techChallenge.domain.order;

import com.fiap.techChallenge.adapters.outbound.entities.OrderEntity;
import com.fiap.techChallenge.domain.user.attendant.Attendant;
import com.fiap.techChallenge.domain.user.customer.Customer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {

    private UUID id;

    private List<OrderItem> items;

    private Customer customer;

    private Attendant attendant;

    private BigDecimal price;

    private LocalDateTime orderDt;

    public Order() {
    }

    public Order(List<OrderItem> items, Customer customer, Attendant attendant, BigDecimal price, LocalDateTime orderDt) {
        this.items = items;
        this.customer = customer;
        this.attendant = attendant;
        this.price = price;
        this.orderDt = orderDt;
    }

    public Order(UUID id, List<OrderItem> items, Customer customer, Attendant attendant, BigDecimal price, LocalDateTime orderDt) {
        this.id = id;
        this.items = items;
        this.customer = customer;
        this.attendant = attendant;
        this.price = price;
        this.orderDt = orderDt;
    }

    public Order(OrderEntity orderEntity) {
        this.id = orderEntity.getId();
        this.price = orderEntity.getPrice();
        this.orderDt = orderEntity.getOrderDt();
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<OrderItem> getItems() {
        return this.items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getOrderDt() {
        return this.orderDt;
    }

    public void setOrderDt(LocalDateTime orderDt) {
        this.orderDt = orderDt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Attendant getAttendant() {
        return attendant;
    }

    public void setAttendant(Attendant attendant) {
        this.attendant = attendant;
    }

    // public void preparation() {
    //     if (this.status != OrderStatus.RECEIVED) {
    //         throw new InvalidOrderStatusTransitionException(
    //                 String.format("Não é possível iniciar preparo. Status atual: %s (Requerido: %s)",
    //                         this.status, OrderStatus.RECEIVED));
    //     }
    //     this.status = OrderStatus.IN_PREPARATION;
    //     this.preparationDt = LocalDateTime.now();
    // }
    // public void ready() {
    //     if (this.status != OrderStatus.IN_PREPARATION) {
    //         throw new InvalidOrderStatusTransitionException(
    //                 String.format("Não é possível marcar como pronto. Status atual: %s (Requerido: %s)",
    //                         this.status, OrderStatus.IN_PREPARATION));
    //     }
    //     this.status = OrderStatus.READY;
    //     this.readyDt = LocalDateTime.now();
    // }
    // public void delivered() {
    //     if (this.status != OrderStatus.READY) {
    //         throw new InvalidOrderStatusTransitionException(
    //                 String.format("Não é possível marcar como entregue. Status atual: %s (Requerido: %s)",
    //                         this.status, OrderStatus.READY));
    //     }
    //     this.status = OrderStatus.DELIVERED;
    //     this.deliveryDt = LocalDateTime.now();
    // }
    // public void finished() {
    //     if (this.status != OrderStatus.DELIVERED) {
    //         throw new InvalidOrderStatusTransitionException(
    //                 String.format("Não é possível finalizar. Status atual: %s (Requerido: %s)",
    //                         this.status, OrderStatus.DELIVERED));
    //     }
    //     this.status = OrderStatus.FINISHED;
    //     this.finishedDt = LocalDateTime.now();
    // }
}
