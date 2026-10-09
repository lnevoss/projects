package io.github.lnevoss.juice_shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lnevoss.juice_shop.model.OrderItem;
import io.github.lnevoss.juice_shop.model.OrderStatus;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
    Optional<OrderItem> findByOrderIdAndProductId(Long orderId, Long productId);
    int deleteByIdAndOrder_User_IdAndOrder_Status(Long orderItemId, Long userId, OrderStatus cart);
}
