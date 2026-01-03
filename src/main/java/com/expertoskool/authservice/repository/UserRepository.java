package com.expertoskool.authservice.repository;

import com.expertoskool.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional findByEmail(String email);

    Optional findById(UUID userId);
}
