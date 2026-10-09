package io.github.lnevoss.juice_shop.service;

import io.github.lnevoss.juice_shop.repository.OrderItemRepository;
import io.github.lnevoss.juice_shop.repository.ProductRepository;
import io.github.lnevoss.juice_shop.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.lnevoss.juice_shop.exception.CartItemNotFoundException;
import io.github.lnevoss.juice_shop.exception.OrderNotFoundException;
import io.github.lnevoss.juice_shop.exception.ProductNotFoundException;
import io.github.lnevoss.juice_shop.exception.UserNotFoundException;
import io.github.lnevoss.juice_shop.model.Order;
import io.github.lnevoss.juice_shop.model.OrderItem;
import io.github.lnevoss.juice_shop.model.OrderStatus;
import io.github.lnevoss.juice_shop.model.Product;
import io.github.lnevoss.juice_shop.model.User;
import io.github.lnevoss.juice_shop.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository,
                        ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public void addToCart(Long userId, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Order cart = orderRepository
                .findByUserIdAndStatus(userId, OrderStatus.CART)
                .orElseGet(() -> createCart(userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException());

        OrderItem item = orderItemRepository
                .findByOrderIdAndProductId(cart.getId(), productId)
                .orElseGet(() -> {
                    OrderItem newItem = new OrderItem();
                    newItem.setOrder(cart);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    return newItem;
                });

        item.setQuantity(item.getQuantity() + quantity);
        orderItemRepository.save(item);
    }

    @Transactional
    public void removeFromCart(Long orderItemId, Long userId) {
        int deleted = orderItemRepository
                .deleteByIdAndOrder_User_IdAndOrder_Status(orderItemId, userId, OrderStatus.CART);
        if (deleted == 0) {
            throw new CartItemNotFoundException();
        }
    }

    @Transactional
    public void updateOrderStatus(Long orderId, Long userId, OrderStatus newStatus) {
        Order order = orderRepository.findByUserIdAndId(userId, orderId)
                .orElseThrow(() -> new OrderNotFoundException());

        if (!isAllowedTransition(order.getStatus(), newStatus)) {
            throw new IllegalStateException(
                    "Cannot change status from " + order.getStatus() + " to " + newStatus);
        }
        order.setStatus(newStatus);
        // no save() needed: the entity is managed inside the transaction
    }

    private Order createCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());

        Order order = new Order();
        order.setCustomerName(user.getName());
        order.setCustomerEmail(user.getEmail());
        order.setCustomerPhone(user.getPhone());
        order.setUser(user);
        order.setStatus(OrderStatus.CART);

        return orderRepository.save(order);
    }

    private boolean isAllowedTransition(OrderStatus from, OrderStatus to) {
        return from == OrderStatus.CART && to == OrderStatus.PENDING;
    }
}
