package com.fiap.techChallenge.application.services.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.techChallenge.application.dto.order.OrderDTO;
import com.fiap.techChallenge.application.dto.order.UpdateOrderStatusHistoryDTO;
import com.fiap.techChallenge.application.dto.order.OrderWithItemsAndStatusDTO;
import com.fiap.techChallenge.application.dto.order.projection.OrderWithStatusAndWaitMinutesProjection;
import com.fiap.techChallenge.application.dto.order.projection.OrderWithStatusProjection;
import com.fiap.techChallenge.application.dto.order.OrderItemDTO;
import com.fiap.techChallenge.application.useCases.notification.NotificationStatusUseCase;
import com.fiap.techChallenge.application.useCases.order.OrderUseCase;
import com.fiap.techChallenge.application.useCases.product.ProductUseCase;
import com.fiap.techChallenge.application.useCases.user.CustomerUseCase;
import com.fiap.techChallenge.domain.enums.Category;
import com.fiap.techChallenge.domain.enums.OrderStatus;
import com.fiap.techChallenge.domain.exceptions.EntityNotFoundException;
import com.fiap.techChallenge.domain.exceptions.order.InvalidOrderStatusException;
import com.fiap.techChallenge.domain.exceptions.order.WrongCategoryOrderException;
import com.fiap.techChallenge.domain.order.Order;
import com.fiap.techChallenge.domain.order.OrderItem;
import com.fiap.techChallenge.domain.order.OrderRepository;
import com.fiap.techChallenge.domain.order.OrderStatusHistory;
import com.fiap.techChallenge.domain.product.Product;
import com.fiap.techChallenge.domain.user.customer.Customer;

@Service
public class OrderServiceImpl implements OrderUseCase {

    private final OrderRepository repository;
    private final ProductUseCase productUseCase;
    private final CustomerUseCase customerUseCase;
    private final NotificationStatusUseCase notificationStatusUseCase;

    public OrderServiceImpl(OrderRepository repository,
            ProductUseCase productUseCase,
            CustomerUseCase customerUseCase,
            NotificationStatusUseCase notificationStatusUseCase) {
        this.repository = repository;
        this.productUseCase = productUseCase;
        this.customerUseCase = customerUseCase;
        this.notificationStatusUseCase = notificationStatusUseCase;
    }

    @Override
    public Order save(OrderDTO dto) {
        List<OrderItem> items = new ArrayList<>();

        for (OrderItemDTO itemDTO : dto.getItems()) {
            Product product = productUseCase.findAvailableProductById(itemDTO.getProductId());
            OrderItem item = new OrderItem(product, itemDTO.getQuantity());
            items.add(item);
        }

        Customer customer = customerUseCase.validate(dto.getCustomerId());

        Order order = new Order();
        order.setCustomer(customer);
        order.setPrice(this.calculatePrice(items));
        order.setItems(items);
        return this.save(order);
    }

    @Override
    public Order save(Order order) {
        List<OrderItem> items = new ArrayList<>();
        List<OrderStatusHistory> statusHistory = new ArrayList<>();

        for (OrderItem item : order.getItems()) {
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

        OrderStatusHistory status = new OrderStatusHistory(
                null,
                OrderStatus.PAGAMENTO_PENDENTE,
                LocalDateTime.now()
        );

        statusHistory.add(status);

        order.setPrice(this.calculatePrice(items));
        order.setItems(items);
        order.setStatusHistory(statusHistory);
        order.setDate(LocalDateTime.now());
        order = repository.save(order);

        if (order.getCustomer().getEmail() != null) {
            notificationStatusUseCase.notifyStatus(order.getCustomer()
                    .getEmail(),
                    order.getId(),
                    "Pedido recebido com sucesso.");
        }

        return order;
    }

    @Override
    public Order updateStatus(UpdateOrderStatusHistoryDTO statusDTO) {
        Order order = repository.validate(statusDTO.getOrderId());
        List<OrderStatusHistory> statusHistory = order.getStatusHistory();

        this.checkStatusTransition(order, statusDTO.getStatus());

        OrderStatusHistory newStatus = new OrderStatusHistory(
                statusDTO.getAttendantId(),
                statusDTO.getStatus(),
                LocalDateTime.now()
        );

        statusHistory.add(newStatus);
        order.setStatusHistory(statusHistory);
        return repository.save(order);
    }

    @Override
    public Order addItem(UUID id, UUID productId, int quantity) {
        Order order = repository.validate(id);
        this.validateOrderIsModifiable(id);

        Product product = productUseCase.findAvailableProductById(productId);
        OrderItem item = new OrderItem(product, quantity);

        List<OrderItem> items = order.getItems();
        items.add(item);

        order.setItems(items);
        order.setPrice(this.calculatePrice(order.getPrice(), product.getPrice(), quantity));
        return repository.save(order);
    }

    @Override
    public Order removeItem(UUID id, UUID productId, int quantity) {
        this.validateOrderIsModifiable(id);
        Order order = repository.validate(id);
        Product product = productUseCase.validate(productId);
        List<OrderItem> items = order.getItems();
        quantity = quantity < 0 ? 0 : quantity;

        OrderItem existingItem = items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado no pedido"));

        if (quantity >= existingItem.getQuantity()) {
            items.remove(existingItem);

        } else {
            existingItem.setQuantity(existingItem.getQuantity() - quantity);
        }

        BigDecimal updatedPrice = order.getPrice()
                .subtract(product.getPrice().multiply(BigDecimal.valueOf(quantity)));

        order.setItems(items);
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
    public List<OrderWithStatusAndWaitMinutesProjection> listTodayOrders() {
        List<String> statusList = List.of(
                OrderStatus.RECEBIDO.name(),
                OrderStatus.EM_PREPARACAO.name(),
                OrderStatus.PRONTO.name(),
                OrderStatus.FINALIZADO.name()
        );

        return repository.listTodayOrders(statusList, 5);
    }

    @Override
    public Order validate(UUID id) {
        return repository.validate(id);
    }

    public boolean isCategoryOutOfOrder(List<OrderItem> currentItems, OrderItem newItem) {
        List<Category> availableCategories = productUseCase.listAvailableCategorys();

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

    public void validateOrderIsModifiable(UUID id) {
        Order order = repository.validate(id);
        OrderStatusHistory lastStatus = order.getStatusHistory().get(0);

        if (lastStatus == null) {
            throw new EntityNotFoundException("Status do Pedido");
        }

        if (lastStatus.getStatus().compareTo(OrderStatus.CANCELADO) == 0
                || lastStatus.getStatus().compareTo(OrderStatus.FINALIZADO) == 0) {
            throw new InvalidOrderStatusException("Não é possivel adicionar um item ao pedido, pois ele está " + lastStatus.getStatus());
        }
    }

    private void checkStatusTransition(Order order, OrderStatus newStatus) {
        this.validateIfStatusAlreadyExists(order, newStatus);

        OrderStatus requiredStatus;
        OrderStatus lastStatus;

        if (!order.getStatusHistory().isEmpty()) {
            lastStatus = order.getStatusHistory().get(0).getStatus();

        } else {
            throw new EntityNotFoundException("Histórico de status não encontrado para o pedido informado");
        }

        switch (newStatus) {
            case PAGAMENTO_PENDENTE, CANCELADO -> {
                break;
            }

            case NAO_PAGO -> {
                requiredStatus = OrderStatus.PAGAMENTO_PENDENTE;
                validateStatus(newStatus, lastStatus, requiredStatus);
            }

            case RECEBIDO -> {
                validateStatus(newStatus, lastStatus, OrderStatus.PAGAMENTO_PENDENTE);
            }

            case EM_PREPARACAO -> {
                requiredStatus = OrderStatus.RECEBIDO;
                validateStatus(newStatus, lastStatus, requiredStatus);
            }

            case PRONTO -> {
                requiredStatus = OrderStatus.EM_PREPARACAO;
                validateStatus(newStatus, lastStatus, requiredStatus);
            }

            case FINALIZADO -> {
                requiredStatus = OrderStatus.PRONTO;
                validateStatus(newStatus, lastStatus, requiredStatus);
            }

        }
    }

    private void validateIfStatusAlreadyExists(Order order,
            OrderStatus status) {
        boolean exists = order.getStatusHistory().stream()
                .anyMatch(history -> history.getStatus().equals(status));

        if (exists) {
            throw new IllegalStateException("O status " + status + " já foi aplicado ao pedido.");
        }
    }

    private void validateStatus(OrderStatus newStatus, OrderStatus currentStatus, OrderStatus... requiredStatusList) {
        for (OrderStatus required : requiredStatusList) {
            if (currentStatus == required) {
                return;
            }
        }
        throw new InvalidOrderStatusException(newStatus, currentStatus, requiredStatusList);
    }

}
