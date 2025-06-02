package com.aesopwow.subsubclipclop.domain.role.repository;

import com.aesopwow.subsubclipclop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(Role.RoleType roleType);
}