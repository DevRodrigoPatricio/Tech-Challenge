package com.fiap.techChallenge.adapters.outbound.storage.order;

import com.fiap.techChallenge.domain.enums.Category;
import com.fiap.techChallenge.domain.enums.ProductStatus;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderItem;
import com.fiap.techChallenge.domain.order.OrderRepository;
import com.fiap.techChallenge.domain.product.Product;
import com.fiap.techChallenge.domain.product.ProductRepository;
import com.fiap.techChallenge.domain.user.CPF;
import com.fiap.techChallenge.domain.user.customer.Customer;
import com.fiap.techChallenge.domain.user.customer.CustomerRepository;
import com.fiap.techChallenge.utils.exceptions.WrongCategoryOrderException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRepository;

class OrderAdapterTest {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private OrderStatusHistoryRepository orderStatusHistoryRepository;
    private OrderAdapter orderAdapter;
    private CustomerRepository customerRepository;

    @BeforeEach
    @SuppressWarnings("unused")
    void setup() {
        orderRepository = mock(OrderRepository.class);
        productRepository = mock(ProductRepository.class);
        orderStatusHistoryRepository = mock(OrderStatusHistoryRepository.class);
        customerRepository  = mock(CustomerRepository.class);
        orderAdapter = new OrderAdapter(orderRepository, productRepository, orderStatusHistoryRepository, customerRepository);
    }

    @Test
    void shouldAddItemSuccessfully() {
        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        Customer customer = new Customer(UUID.randomUUID(), "Cliente Teste", "cliente@teste.com", new CPF("12345678900"), false);
        List<OrderItem> items = new ArrayList<>();

        Product product = new Product(productId, "Lanche X", "X", new BigDecimal(10), Category.LANCHE, ProductStatus.DISPONIVEL, "img.jpg");
        Order order = new Order(items, customer, null, BigDecimal.ZERO, LocalDateTime.now());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.listAvaiableCategorys()).thenReturn(List.of(Category.LANCHE));
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderAdapter.addItem(orderId, productId, 2);

        assertEquals(1, result.getItems().size());
        assertEquals(new BigDecimal(20), result.getPrice());
    }

    @Test
    void shouldThrowExceptionWhenAddItemWithWrongCategoryOrder() {
        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        Customer customer = new Customer(UUID.randomUUID(), "Cliente Teste", "cliente@teste.com", new CPF("12345678900"), false);
        List<OrderItem> items = new ArrayList<>();

        Product product = new Product(productId, "Batata Frita", "Batata", BigDecimal.TEN, Category.ACOMPANHAMENTO, ProductStatus.DISPONIVEL, "img.jpg");
        Order order = new Order(items, customer, null, BigDecimal.ZERO, LocalDateTime.now());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.listAvaiableCategorys()).thenReturn(List.of(Category.LANCHE, Category.BEBIDA));

        WrongCategoryOrderException ex = assertThrows(
                WrongCategoryOrderException.class,
                () -> orderAdapter.addItem(orderId, productId, 1)
        );

        String categoryList = Arrays.stream(Category.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));

        assertTrue(ex.getMessage().equalsIgnoreCase("Os produtos devem ser selecionados na seguinte ordem: " + categoryList));
    }

    @Test
    void shouldRemoveItemPartially() {
        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        Customer customer = new Customer(UUID.randomUUID(), "Cliente Teste", "cliente@teste.com", new CPF("12345678900"), false);
        List<OrderItem> items = new ArrayList<>();

        Product product = new Product(productId, "Batata Frita", "Batata", BigDecimal.valueOf(5), Category.ACOMPANHAMENTO, ProductStatus.DISPONIVEL, "img.jpg");

        OrderItem item = new OrderItem(product, 3);
        items.add(item);

        Order order = new Order(items, customer, null, BigDecimal.valueOf(15), LocalDateTime.now());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderAdapter.removeItem(orderId, productId, 2);

        assertEquals(1, result.getItems().size());
        assertEquals(1, result.getItems().get(0).getQuantity());
        assertEquals(BigDecimal.valueOf(5), result.getPrice());
    }

    @Test
    void shouldRemoveItemCompletely() {
        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        Customer customer = new Customer(UUID.randomUUID(), "Cliente Teste", "cliente@teste.com", new CPF("12345678900"), false);
        List<OrderItem> items = new ArrayList<>();

        Product product = new Product(productId, "Bebida Z", "Refrigerante", BigDecimal.valueOf(5), Category.BEBIDA, ProductStatus.DISPONIVEL, "img.jpg");

        OrderItem item = new OrderItem(product, 2);
        items.add(item);

        Order order = new Order(items, customer, null, BigDecimal.valueOf(10), LocalDateTime.now());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderAdapter.removeItem(orderId, productId, 2);

        assertTrue(result.getItems().isEmpty());
        assertEquals(BigDecimal.ZERO, result.getPrice());
    }
}
