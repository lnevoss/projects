package io.github.lnevoss.juice_shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lnevoss.juice_shop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}


