package com.aesopwow.subsubclipclop.domain.membership.repository;

import com.aesopwow.subsubclipclop.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Optional<Membership> findById(Long id);
}
