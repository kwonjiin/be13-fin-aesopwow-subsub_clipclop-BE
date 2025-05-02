package com.aesopwow.subsubclipclop.domain.auth.repository;

import com.aesopwow.subsubclipclop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
