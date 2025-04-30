package com.aesopwow.subsubclipclop.domain.analysis.repository.insight;

import com.aesopwow.subsubclipclop.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CohortAnalysisInsightCompanyJpaRepository extends JpaRepository<Company, Long> {
}