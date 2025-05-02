package com.aesopwow.subsubclipclop.domain.analysis.repository.behaviorpattern;

import com.aesopwow.subsubclipclop.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CohortAnalysisBehaviorPatternCompanyJpaRepository extends JpaRepository<Company, Long> {
}