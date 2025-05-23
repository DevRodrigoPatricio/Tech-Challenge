package com.fiap.techChallenge.application.services.order;

import com.fiap.techChallenge.application.dto.order.OrderRequestDTO;
import com.fiap.techChallenge.application.useCases.notification.NotificationStatusUseCase;
import com.fiap.techChallenge.application.useCases.order.OrderUseCase;
import com.fiap.techChallenge.domain.enums.Category;
import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.domain.exceptions.EntityNotFoundException;
import com.fiap.techChallenge.domain.exceptions.order.InvalidOrderStatusException;
import com.fiap.techChallenge.domain.exceptions.order.WrongCategoryOrderException;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderItem;
import com.fiap.techChallenge.domain.order.OrderRepository;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistory;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRepository;
import com.fiap.techChallenge.domain.product.Product;
import com.fiap.techChallenge.domain.product.ProductRepository;
import com.fiap.techChallenge.domain.user.customer.Customer;
import com.fiap.techChallenge.domain.user.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl  implements OrderUseCase {

    private final OrderRepository repository;
    private final ProductRepository productRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final CustomerRepository customerRepository;
    private final NotificationStatusUseCase notificationStatusUseCase;


    public OrderServiceImpl(OrderRepository repository, ProductRepository productRepository,
            OrderStatusHistoryRepository orderStatusHistoryRepository,
            CustomerRepository customerRepository,
            NotificationStatusUseCase notificationStatusUseCase) {
    this.repository = repository;
        this.productRepository = productRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
        this.customerRepository = customerRepository;
        this.notificationStatusUseCase = notificationStatusUseCase;
    }

    
    @Override
    public Order save(OrderRequestDTO request) {
        List<OrderItem> items = new ArrayList<>();
        BigDecimal price = new BigDecimal(0);

        for (OrderItem item : request.getItems()) {
            if (this.canAddItem(items, item)) {
                items.add(item);

            } else {
                throw new WrongCategoryOrderException();
            }

            price = calculatePrice(price, item.getUnitPrice(), item.getQuantity());
        }

        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Cliente"));

        Order order = new Order();
        order.setItems(request.getItems());
        order.setCustomer(customer);
        order.setPrice(price);
        order.setItems(items);
        order.setOrderDt(LocalDateTime.now());
        order = repository.save(order);

        this.insertStatus(order.getId(), OrderStatus.RECEBIDO);

        if (customer.getEmail() != null) {
            notificationStatusUseCase.notifyStatus(customer.getEmail(), order.getId(), "Pedido recebido com sucesso.");
        }

        return order;
    }

    @Override
    public Order addItem(UUID id, UUID productId, int quantity) {
        Order order = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pedido"));
        OrderStatusHistory status = orderStatusHistoryRepository.findLast(id).orElseThrow(() -> new EntityNotFoundException("Status do Pedido"));

        if (status.getStatus().compareTo(OrderStatus.CANCELADO) == 0
                || status.getStatus().compareTo(OrderStatus.FINALIZADO) == 0) {
            throw new InvalidOrderStatusException("Não é possivel adicionar um item ao pedido, pois ele está " + status.getStatus());
        }

        Product product = productRepository.findAvaiableProductById(productId);
        OrderItem newItem = new OrderItem(product, quantity);
        List<OrderItem> items = order.getItems();

        if (this.canAddItem(items, newItem)) {
            items.add(newItem);

        } else {
            throw new WrongCategoryOrderException();
        }

        order.setPrice(this.calculatePrice(order.getPrice(), product.getPrice(), quantity));
        return repository.save(order);
    }

    @Override
    public Order removeItem(UUID id, UUID productId, int quantity) {
        Order order = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pedido"));
        OrderStatusHistory status = orderStatusHistoryRepository.findLast(id).orElseThrow(() -> new EntityNotFoundException("Status do Pedido"));

        if (status.getStatus().compareTo(OrderStatus.CANCELADO) == 0
                || status.getStatus().compareTo(OrderStatus.FINALIZADO) == 0) {
            throw new InvalidOrderStatusException("Não é possivel remover um item do pedido, pois ele está " + status.getStatus());
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Produto"));

        List<OrderItem> items = order.getItems();

        OrderItem existingItem = items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado no pedido"));

        if (quantity >= existingItem.getQuantity()) {
            items.remove(existingItem);

        } else {
            quantity = quantity < 0 ? 0 : quantity;
            existingItem.setQuantity(existingItem.getQuantity() - quantity);
        }

        BigDecimal updatedPrice = order.getPrice()
                .subtract(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        order.setPrice(updatedPrice.max(BigDecimal.ZERO));

        return repository.save(order);
    }

    @Override
    public Order findById(UUID id) {
        return repository.findById(id).orElse(Order.empty());
    }

    @Override
    public List<Order> listByClient(UUID clientId) {
        return repository.listByClient(clientId);
    }

    @Override
    public List<Order> listByPeriod(LocalDateTime initialDt, LocalDateTime finalDt) {
        return repository.listByPeriod(initialDt, finalDt);
    }

    @Override
    public void delete(UUID id) {
        Order order = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pedido"));
        this.insertStatus(order.getId(), OrderStatus.CANCELADO);
    }

    public boolean canAddItem(List<OrderItem> currentItems, OrderItem newItem) {
        List<Category> availableCategories = productRepository.listAvaiableCategorys();

        List<Category> orderedCategories = Arrays.stream(Category.values())
                .filter(availableCategories::contains)
                .toList();

        Category newCategory = newItem.getCategory();
        int newIndex = orderedCategories.indexOf(newCategory);

        if (newIndex == -1) {
            return false;
        }

        if (currentItems.isEmpty()) {
            return true;
        }

        List<Integer> usedIndexes = currentItems.stream()
                .map(OrderItem::getCategory)
                .map(orderedCategories::indexOf)
                .distinct()
                .toList();

        int maxUsedIndex = usedIndexes.stream()
                .mapToInt(i -> i)
                .max()
                .orElse(-1);

        boolean isCategoryOutOfOrder = newIndex < maxUsedIndex && !usedIndexes.contains(newIndex);

        return !isCategoryOutOfOrder;
    }

    public BigDecimal calculatePrice(BigDecimal currentOrderValue, BigDecimal productPrice, int quantity) {
        if (productPrice != null && productPrice.compareTo(new BigDecimal(0)) != 0) {
            productPrice = productPrice.multiply(BigDecimal.valueOf(quantity));
        }

        return currentOrderValue.add(productPrice);
    }

    private void insertStatus(UUID orderId, OrderStatus status) {
        OrderStatusHistory history = new OrderStatusHistory(
                orderId,
                status,
                LocalDateTime.now()
        );

        orderStatusHistoryRepository.save(history);
    }
}