package com.aesopwow.subsubclipclop.domain.dbinfo.repository;

import com.aesopwow.subsubclipclop.entity.DbInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbInfoRepository extends JpaRepository<DbInfo, Long> {
}
