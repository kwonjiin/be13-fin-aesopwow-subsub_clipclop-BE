package com.aesopwow.subsubclipclop.domain.user.repository;

import com.aesopwow.subsubclipclop.entity.InfoColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InfoColumnRepository extends JpaRepository<InfoColumn, Long> {

    Optional<InfoColumn> findFirstByInfoDb_InfoDbNo(Long infoDbNo);
}