package com.aesopwow.subsubclipclop.domain.role.repository;

import com.aesopwow.subsubclipclop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Byte> {
    Role findByName(Role.RoleType name);
}

