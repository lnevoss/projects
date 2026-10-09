package io.github.lnevoss.juice_shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lnevoss.juice_shop.model.Order;
import io.github.lnevoss.juice_shop.model.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    Optional<Order> findByUserIdAndStatus(Long userId, OrderStatus status);
    Optional<Order> findByUserIdAndId(Long userId, Long id);
}


