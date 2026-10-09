package io.github.lnevoss.juice_shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lnevoss.juice_shop.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailOrName(String email, String name);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}