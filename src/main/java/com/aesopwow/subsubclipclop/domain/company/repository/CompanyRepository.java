package com.aesopwow.subsubclipclop.domain.company.repository;

import com.aesopwow.subsubclipclop.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
