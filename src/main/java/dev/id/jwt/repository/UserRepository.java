package dev.id.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.id.jwt.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

