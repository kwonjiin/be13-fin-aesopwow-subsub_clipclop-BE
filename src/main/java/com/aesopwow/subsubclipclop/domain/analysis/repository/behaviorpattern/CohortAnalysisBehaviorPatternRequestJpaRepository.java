package com.aesopwow.subsubclipclop.domain.analysis.repository.behaviorpattern;

import com.aesopwow.subsubclipclop.entity.RequestList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CohortAnalysisBehaviorPatternRequestJpaRepository extends JpaRepository<RequestList, Long> {
}