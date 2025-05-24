package com.fiap.techChallenge.application.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.techChallenge.application.useCases.NotificationStatusUseCase;
import com.fiap.techChallenge.application.useCases.OrderUseCase;
import com.fiap.techChallenge.domain.enums.Category;
import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderItem;
import com.fiap.techChallenge.domain.order.OrderRepository;
import com.fiap.techChallenge.domain.order.dto.OrderWithItemsAndStatusDTO;
import com.fiap.techChallenge.domain.order.projection.OrderWithStatusAndWaitMinutesProjection;
import com.fiap.techChallenge.domain.order.projection.OrderWithStatusProjection;
import com.fiap.techChallenge.domain.order.request.OrderItemRequest;
import com.fiap.techChallenge.domain.order.request.OrderRequest;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistory;
import com.fiap.techChallenge.domain.order.status.OrderStatusHistoryRepository;
import com.fiap.techChallenge.domain.product.Product;
import com.fiap.techChallenge.domain.product.ProductRepository;
import com.fiap.techChallenge.domain.user.customer.Customer;
import com.fiap.techChallenge.domain.user.customer.CustomerRepository;
import com.fiap.techChallenge.utils.exceptions.EntityNotFoundException;
import com.fiap.techChallenge.utils.exceptions.InvalidOrderStatusException;
import com.fiap.techChallenge.utils.exceptions.WrongCategoryOrderException;

@Service
public class OrderServiceImpl implements OrderUseCase {

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
    public Order save(OrderRequest request) {
        List<OrderItem> items = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findAvailableProductById(itemRequest.getProductId());
            OrderItem item = new OrderItem(product, itemRequest.getQuantity());

            if (!this.isCategoryOutOfOrder(items, item)) {
                if (!this.isItemInAlreadyOrder(items, item)) {
                    items.add(item);

                } else {
                    this.increaseItemQuantity(items, item);
                }

            } else {
                throw new WrongCategoryOrderException();
            }
        }

        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("Cliente"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setPrice(this.calculatePrice(items));
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
        Order order = repository.validate(id);
        OrderStatusHistory status = orderStatusHistoryRepository.findLast(id).orElseThrow(() -> new EntityNotFoundException("Status do Pedido"));

        if (status.getStatus().compareTo(OrderStatus.CANCELADO) == 0
                || status.getStatus().compareTo(OrderStatus.FINALIZADO) == 0) {
            throw new InvalidOrderStatusException("Não é possivel adicionar um item ao pedido, pois ele está " + status.getStatus());
        }

        Product product = productRepository.findAvailableProductById(productId);

        order.setPrice(this.calculatePrice(order.getPrice(), product.getPrice(), quantity));
        return repository.save(order);
    }

    @Override
    public Order removeItem(UUID id, UUID productId, int quantity) {
        Order order = repository.validate(id);
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
    public OrderWithItemsAndStatusDTO findById(UUID id) {
        return repository.findById(id).orElse(new OrderWithItemsAndStatusDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of()
        )
        );
    }

    @Override
    public List<OrderWithStatusProjection> listByPeriod(LocalDateTime initialDt, LocalDateTime finalDt) {
        return repository.listByPeriod(initialDt, finalDt);
    }

    @Override
    public void delete(UUID id) {
        Order order = repository.validate(id);
        this.insertStatus(order.getId(), OrderStatus.CANCELADO);
    }

    @Override
    public List<OrderWithStatusAndWaitMinutesProjection> listTodayOrders() {
        List<String> statusList = List.of(
                OrderStatus.RECEBIDO.name(),
                OrderStatus.EM_PREPARACAO.name(),
                OrderStatus.PRONTO.name(),
                OrderStatus.FINALIZADO.name()
        );

        return repository.listTodayOrders(statusList, 5);
    }

    public boolean isCategoryOutOfOrder(List<OrderItem> currentItems, OrderItem newItem) {
        List<Category> availableCategories = productRepository.listAvaiableCategorys();

        List<Category> orderedCategories = Arrays.stream(Category.values())
                .filter(availableCategories::contains)
                .toList();

        Category newCategory = newItem.getCategory();
        int newIndex = orderedCategories.indexOf(newCategory);

        if (newIndex == -1) {
            return true;
        }

        if (currentItems.isEmpty()) {
            return false;
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

        boolean isCategoryOutOfOrder = newIndex < maxUsedIndex;
        return isCategoryOutOfOrder;
    }

    public BigDecimal calculatePrice(BigDecimal currentOrderValue, BigDecimal productPrice, int quantity) {
        if (productPrice != null && productPrice.compareTo(new BigDecimal(0)) != 0) {
            productPrice = productPrice.multiply(BigDecimal.valueOf(quantity));
        }

        return currentOrderValue.add(productPrice);
    }

    public BigDecimal calculatePrice(List<OrderItem> items) {
        BigDecimal price = new BigDecimal(0);
        for (OrderItem item : items) {
            price = this.calculatePrice(price, item.getUnitPrice(), item.getQuantity());
        }

        return price;
    }

    public void increaseItemQuantity(List<OrderItem> currentItems, OrderItem newItem) {
        for (OrderItem item : currentItems) {
            if (item.getProductId() == newItem.getProductId()) {
                int index = currentItems.indexOf(item);
                OrderItem itemWithNewQuantity = currentItems.get(index);
                itemWithNewQuantity.setQuantity(itemWithNewQuantity.getQuantity() + newItem.getQuantity());
                currentItems.set(index, itemWithNewQuantity);
            }
        }
    }

    public boolean isItemInAlreadyOrder(List<OrderItem> currentItems, OrderItem newItem) {
        boolean isItemInOrder = false;

        for (OrderItem item : currentItems) {
            if (item.getProductId() == newItem.getProductId()) {
                isItemInOrder = true;
                break;
            }
        }

        return isItemInOrder;
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
