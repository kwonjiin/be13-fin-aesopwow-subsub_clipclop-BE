package com.aesopwow.subsubclipclop.domain.analysis.service;

import com.aesopwow.subsubclipclop.domain.analysis.repository.AnalysisRepository;
import com.aesopwow.subsubclipclop.entity.Analysis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService{
    private final AnalysisRepository analysisRepository;

    @Override
    public Analysis getAnalysisByNo(Long analysisNo) {
        Optional<Analysis> analysis = analysisRepository.findById(analysisNo);

        return analysis.get();
    }
}
