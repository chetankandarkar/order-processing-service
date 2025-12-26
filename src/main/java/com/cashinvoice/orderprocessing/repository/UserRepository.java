package com.cashinvoice.orderprocessing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashinvoice.orderprocessing.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}

