package com.fiap.techChallenge.domain.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fiap.techChallenge.domain.user.attendant.Attendant;
import com.fiap.techChallenge.domain.user.customer.Customer;

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

    public static Order empty() {
        return new Order();
    }
}
