package com.fiap.techChallenge.adapters.outbound.entities.order;

import com.fiap.techChallenge.adapters.outbound.entities.user.CustomerEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "`order`")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItemEmbeddable> items;

    @ElementCollection
    @CollectionTable(name = "order_status_history", joinColumns = @JoinColumn(name = "order_id"))
    @OrderBy("date DESC")
    private List<OrderStatusEmbeddable> statusHistory;

    @ManyToOne
    private CustomerEntity customer;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public OrderEntity() {
    }

    public OrderEntity(UUID id, List<OrderItemEmbeddable> items, List<OrderStatusEmbeddable> statusHistory, CustomerEntity customer, BigDecimal price, LocalDateTime date) {
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

    public List<OrderItemEmbeddable> getItems() {
        return this.items;
    }

    public void setItems(List<OrderItemEmbeddable> items) {
        this.items = items;
    }

    public List<OrderStatusEmbeddable> getStatusHistory() {
        return this.statusHistory;
    }

    public void setStatusHistory(List<OrderStatusEmbeddable> statusHistory) {
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


    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customerId) {
        this.customer = customerId;
    }

}
