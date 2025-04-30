package com.aesopwow.subsubclipclop.domain.analysis.repository.insight;

import com.aesopwow.subsubclipclop.entity.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CohortAnalysisInsightJpaRepository extends JpaRepository<Analysis, Byte> {
}