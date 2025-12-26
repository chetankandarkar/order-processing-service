package com.cashinvoice.orderprocessing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cashinvoice.orderprocessing.model.RoleEntity;
import com.cashinvoice.orderprocessing.security.Role;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(Role name);
}

