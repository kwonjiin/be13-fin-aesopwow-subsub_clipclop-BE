package com.aesopwow.subsubclipclop.domain.analysis.service.behaviorpattern;

import com.aesopwow.subsubclipclop.domain.analysis.dto.behaviorpattern.CohortAnalysisBehaviorPatternRequestDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.behaviorpattern.CohortAnalysisBehaviorPatternResponseDto;
import com.aesopwow.subsubclipclop.domain.analysis.repository.behaviorpattern.*;
import com.aesopwow.subsubclipclop.entity.Analysis;
import com.aesopwow.subsubclipclop.entity.Company;
import com.aesopwow.subsubclipclop.entity.RequestList;
import com.aesopwow.subsubclipclop.global.enums.ErrorCode;
import com.aesopwow.subsubclipclop.global.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CohortAnalysisBehaviorPatternServiceImpl implements CohortAnalysisBehaviorPatternService {
    private final CohortAnalysisBehaviorPatternRepository cohortAnalysisBehaviorPatternRepository;
    private final CohortAnalysisBehaviorPatternJpaRepository cohortAnalysisBehaviorPatternJpaRepository;
    private final CohortAnalysisBehaviorPatternCompanyJpaRepository cohortAnalysisBehaviorPatternCompanyJpaRepository;
    private final CohortAnalysisBehaviorPatternRequestJpaRepository cohortAnalysisBehaviorPatternRequestJpaRepository;

    @Override
    @Transactional
    public CohortAnalysisBehaviorPatternResponseDto fetchBehaviorPattern(CohortAnalysisBehaviorPatternRequestDto requestDto) {
        var result = cohortAnalysisBehaviorPatternRepository.requestBehaviorPattern();
        int featureValue = result.getFeature();

        Analysis analysis = cohortAnalysisBehaviorPatternJpaRepository.findById((byte) 1)
                .orElseThrow(() -> new CustomException(ErrorCode.ANALYSIS_NOT_FOUND));

        Company company = cohortAnalysisBehaviorPatternCompanyJpaRepository.findById(requestDto.getCompanyNo())
                .orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));

        RequestList savedRequestList = RequestList.builder()
                .analysis(analysis)
                .company(company)
                .build();

        cohortAnalysisBehaviorPatternRequestJpaRepository.save(savedRequestList);

        if (featureValue >= 8) {
            return new CohortAnalysisBehaviorPatternResponseDto("상관관계 높음", "시청률이 높을수록 구독 전환율이 높습니다.");
        } else if (featureValue >= 4) {
            return new CohortAnalysisBehaviorPatternResponseDto("상관관계 중간", "일정 수준의 관계성이 확인됩니다.");
        } else {
            return new CohortAnalysisBehaviorPatternResponseDto("상관관계 낮음", "강한 관계성은 확인되지 않습니다.");
        }
    }
}
