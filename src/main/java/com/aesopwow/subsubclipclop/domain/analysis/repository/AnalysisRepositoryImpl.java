// domain/analysis/repository/AnalysisRepositoryImpl.java
package com.aesopwow.subsubclipclop.domain.analysis.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AnalysisRepositoryImpl implements AnalysisRepository {

    @Override
    public Integer requestBehaviorPattern() {
        return 0;
    }
}