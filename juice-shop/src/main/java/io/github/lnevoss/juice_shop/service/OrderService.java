package io.github.lnevoss.juice_shop.service;

import io.github.lnevoss.juice_shop.repository.OrderItemRepository;
import io.github.lnevoss.juice_shop.repository.ProductRepository;
import io.github.lnevoss.juice_shop.repository.UserRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

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

    OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional 
    public void addToCart(Long userId, Long productId, int quantity){
        Order cart = orderRepository
            .findByUserIdAndStatus(userId, OrderStatus.CART)
            .orElseGet(() -> createCart(userId));

        Product product = productRepository
            .findById(productId)
            .orElseThrow();

        OrderItem item = orderItemRepository
            .findByOrderIdAndProductId(cart.getId(), productId)
            .orElse(null);

        if(item == null){
            item = new OrderItem();
            item.setOrder(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
        } else{
            item.setQuantity(item.getQuantity() + quantity);
        }

        orderItemRepository.save(item);
    }

    @Transactional 
    public void removeFromCart(Long orderItemId, Long userId){
        orderItemRepository.deleteByIdAndOrder_User_Id(orderItemId, userId);
    }

    public void updateOrderStatus(Long orderId, Long userId, OrderStatus status){
        Order order = orderRepository
            .findByUserIdAndId(userId, orderId)
            .orElse(null);

        if(order != null){
            order.setStatus(status);
            orderRepository.save(order);
        }
    }

    public Order createCart(Long userId){
        User user = userRepository.findById(userId).get();

        Order order = new Order();
        order.setCustomerName(user.getName());
        order.setCustomerEmail(user.getEmail());
        order.setCustomerPhone(user.getPhone());
        order.setUser(user);
        order.setStatus(OrderStatus.CART);
        
        return order;
    }
}

