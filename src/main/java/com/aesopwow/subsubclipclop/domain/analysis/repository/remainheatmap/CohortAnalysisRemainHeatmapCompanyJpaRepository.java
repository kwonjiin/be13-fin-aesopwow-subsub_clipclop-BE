package com.aesopwow.subsubclipclop.domain.analysis.repository.remainheatmap;

import com.aesopwow.subsubclipclop.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CohortAnalysisRemainHeatmapCompanyJpaRepository extends JpaRepository<Company, Long> {
}