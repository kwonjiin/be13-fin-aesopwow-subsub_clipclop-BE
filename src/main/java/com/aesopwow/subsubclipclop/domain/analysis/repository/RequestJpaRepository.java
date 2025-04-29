package com.aesopwow.subsubclipclop.domain.analysis.repository;

import com.aesopwow.subsubclipclop.entity.RequestList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestJpaRepository extends JpaRepository<RequestList, Long> {
}