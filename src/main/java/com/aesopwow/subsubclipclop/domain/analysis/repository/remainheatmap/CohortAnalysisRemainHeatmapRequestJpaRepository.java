package com.aesopwow.subsubclipclop.domain.analysis.repository.remainheatmap;

import com.aesopwow.subsubclipclop.entity.RequestList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CohortAnalysisRemainHeatmapRequestJpaRepository extends JpaRepository<RequestList, Long> {
}