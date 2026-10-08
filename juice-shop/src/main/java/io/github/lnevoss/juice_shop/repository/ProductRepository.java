package io.github.lnevoss.juice_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lnevoss.juice_shop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
