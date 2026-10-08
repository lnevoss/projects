package io.github.lnevoss.juice_shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lnevoss.juice_shop.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
    Optional<OrderItem> findByOrderIdAndProductId(Long orderId, Long productId);
    void deleteByOrderItemIdAndUserId(Long id, Long userId);
}
