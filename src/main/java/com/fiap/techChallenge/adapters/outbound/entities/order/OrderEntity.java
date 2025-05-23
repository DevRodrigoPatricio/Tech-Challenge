package com.fiap.techChallenge.adapters.outbound.entities.order;

import com.fiap.techChallenge.adapters.outbound.entities.user.AttendantEntity;
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
    @CollectionTable(name = "orderItems", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItemEmbeddable> items;

    @ManyToOne
    private CustomerEntity customer;

    @ManyToOne
    private AttendantEntity attendant;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "orderDt", nullable = false)
    private LocalDateTime orderDt;

    public OrderEntity() {
    }

    public OrderEntity(UUID id, List<OrderItemEmbeddable> items, CustomerEntity customer, AttendantEntity attendant, BigDecimal price, LocalDateTime orderDt) {
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

    public List<OrderItemEmbeddable> getItems() {
        return this.items;
    }

    public void setItems(List<OrderItemEmbeddable> items) {
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

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customerId) {
        this.customer = customerId;
    }

    public AttendantEntity getAttendant() {
        return attendant;
    }

    public void setAttendant(AttendantEntity attendantId) {
        this.attendant = attendantId;
    }
}
