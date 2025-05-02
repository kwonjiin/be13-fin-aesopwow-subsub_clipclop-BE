package com.aesopwow.subsubclipclop.domain.analysis.repository.insight;

import com.aesopwow.subsubclipclop.entity.RequestList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CohortAnalysisInsightRequestJpaRepository extends JpaRepository<RequestList, Long> {
}