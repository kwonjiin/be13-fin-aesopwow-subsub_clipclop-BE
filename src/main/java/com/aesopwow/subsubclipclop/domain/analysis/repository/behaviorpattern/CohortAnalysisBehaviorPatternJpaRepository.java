package com.aesopwow.subsubclipclop.domain.analysis.repository.behaviorpattern;

import com.aesopwow.subsubclipclop.entity.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CohortAnalysisBehaviorPatternJpaRepository extends JpaRepository<Analysis, Byte> {
}