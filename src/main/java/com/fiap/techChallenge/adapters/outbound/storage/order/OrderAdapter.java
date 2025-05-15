package com.fiap.techChallenge.adapters.outbound.storage.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fiap.techChallenge.domain.enums.Category;
import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderItem;
import com.fiap.techChallenge.domain.order.OrderRepository;
import com.fiap.techChallenge.domain.order.OrderRequest;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistory;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRepository;
import com.fiap.techChallenge.domain.product.Product;
import com.fiap.techChallenge.domain.product.ProductRepository;
import com.fiap.techChallenge.utils.exceptions.EntityNotFoundException;
import com.fiap.techChallenge.utils.exceptions.WrongCategoryOrderException;

@Component
public class OrderAdapter implements OrderPort {

    private final OrderRepository repository;
    private final ProductRepository productRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;

    public OrderAdapter(OrderRepository repository, ProductRepository productRepository, OrderStatusHistoryRepository orderStatusHistoryRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
    }

    @Override
    public Order save(OrderRequest request) {
        List<OrderItem> items = request.getItems();
        BigDecimal price = new BigDecimal(0);

        for (OrderItem item : items) {
            price = calculatePrice(price, item.getUnitPrice(), item.getQuantity());
        }

        Order order = new Order();
        order.setItems(request.getItems());
        order.setClientId(request.getClientId());
        order.setPrice(price);
        order.setOrderDt(LocalDateTime.now());
        order = repository.save(order);

        this.insertStatus(order.getId(), OrderStatus.RECEIVED);

        return order;
    }

    @Override
    public Order addItem(UUID id, UUID productId, int quantity) {
        Order order = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pedido"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Produto"));

        OrderItem newItem = new OrderItem(product, quantity);

        List<OrderItem> items = order.getItems();

        if (this.canAddItem(items, newItem)) {
            items.add(newItem);

        } else {
            String categoryList = Arrays.stream(Category.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));

            throw new WrongCategoryOrderException(categoryList);
        }

        order.setPrice(this.calculatePrice(order.getPrice(), product.getPrice(), quantity));
        return repository.save(order);
    }

    @Override
    public Order removeItem(UUID id, UUID productId, int quantity) {
        Order order = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pedido"));
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
    public Optional<Order> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Order> listByClient(String clientId) {
        return repository.listByClient(clientId);
    }

    @Override
    public List<Order> listByPeriod(LocalDateTime initialDt, LocalDateTime finalDt) {
        return repository.listByPeriod(initialDt, finalDt);
    }

    @Override
    public void delete(UUID id) {
        Order order = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pedido"));
        this.insertStatus(order.getId(), OrderStatus.CANCELED);
    }

    public boolean canAddItem(List<OrderItem> items, OrderItem newItem) {
        List<Category> avaiablesCategorys = productRepository.listAvaiableCategorys();
        avaiablesCategorys = List.of(Category.values()).stream()
                .filter(avaiablesCategorys::contains)
                .toList();

        Category newItemCategory = newItem.getCategory();

        if (items.isEmpty()) {
            return avaiablesCategorys.contains(newItemCategory);
        }

        int maxUsedIndex = items.stream()
                .map(OrderItem::getCategory)
                .mapToInt(avaiablesCategorys::indexOf)
                .max()
                .orElse(-1);

        int newIndex = avaiablesCategorys.indexOf(newItemCategory);

        return newIndex <= maxUsedIndex || newIndex == maxUsedIndex + 1;
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
