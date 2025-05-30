package com.fiap.techChallenge.domain.core.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fiap.techChallenge.domain.core.user.customer.Customer;

public class Order {

    private UUID id;

    private List<OrderItem> items;

    private List<OrderStatusHistory> statusHistory;

    private Customer customer;

    private BigDecimal price;

    private LocalDateTime date;

    public Order() {
    }

    public Order(List<OrderItem> items, List<OrderStatusHistory> statusHistory, Customer customer, BigDecimal price, LocalDateTime date) {
        this.items = items;
        this.statusHistory = statusHistory;
        this.customer = customer;
        this.price = price;
        this.date = date;
    }

    public Order(UUID id, List<OrderItem> items, List<OrderStatusHistory> statusHistory, Customer customer, BigDecimal price, LocalDateTime date) {
        this.id = id;
        this.items = items;
        this.statusHistory = statusHistory;
        this.customer = customer;
        this.price = price;
        this.date = date;
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

    public List<OrderStatusHistory> getStatusHistory() {
        return this.statusHistory;
    }

    public void setStatusHistory(List<OrderStatusHistory> statusHistory) {
        this.statusHistory = statusHistory;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public static Order empty() {
        return new Order();
    }
}
