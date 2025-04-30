package com.aesopwow.subsubclipclop.domain.analysis.repository.remainheatmap;

import com.aesopwow.subsubclipclop.entity.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CohortAnalysisRemainHeatmapJpaRepository extends JpaRepository<Analysis, Byte> {
}