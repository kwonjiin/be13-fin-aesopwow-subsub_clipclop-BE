package com.aesopwow.subsubclipclop.domain.analysis.service.behaviorpattern;

import com.aesopwow.subsubclipclop.domain.analysis.dto.behaviorpattern.CohortAnalysisBehaviorPatternRequestDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.behaviorpattern.CohortAnalysisBehaviorPatternResponseDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.behaviorpattern.CohortAnalysisBehaviorPatternAnalysisResultDto;
import com.aesopwow.subsubclipclop.domain.analysis.repository.behaviorpattern.CohortAnalysisBehaviorPatternRepository;
import com.aesopwow.subsubclipclop.domain.analysis.repository.behaviorpattern.CohortAnalysisBehaviorPatternJpaRepository;
import com.aesopwow.subsubclipclop.domain.analysis.repository.behaviorpattern.CohortAnalysisBehaviorPatternCompanyJpaRepository;
import com.aesopwow.subsubclipclop.domain.analysis.repository.behaviorpattern.CohortAnalysisBehaviorPatternRequestJpaRepository;
import com.aesopwow.subsubclipclop.entity.Analysis;
import com.aesopwow.subsubclipclop.entity.Company;
import com.aesopwow.subsubclipclop.entity.RequestList;
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

        // 1. Python 서버에서 분석 결과 수신 (DTO 기반으로 변경)
        CohortAnalysisBehaviorPatternAnalysisResultDto result =
                cohortAnalysisBehaviorPatternRepository.requestBehaviorPattern();

        int featureValue = result.getFeature();

        // 2. Analysis 엔티티 조회
        Analysis analysis = cohortAnalysisBehaviorPatternJpaRepository.findById((byte) 1)
                .orElseThrow(() -> new RuntimeException("Analysis not found"));

        // 3. Company 엔티티 조회
        Company company = cohortAnalysisBehaviorPatternCompanyJpaRepository.findById(requestDto.getCompanyNo())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // 4. Request 저장
        RequestList savedRequestList = RequestList.builder()
                .analysis(analysis)
                .company(company)
                .build();
        cohortAnalysisBehaviorPatternRequestJpaRepository.save(savedRequestList);

        // 5. 수치 해석 → 응답 생성
        if (featureValue >= 8) {
            return new CohortAnalysisBehaviorPatternResponseDto(
                    "상관관계 높음",
                    "시청률이 높을 수록 구독을 더 하는 경향성이 확실히 있습니다."
            );
        } else if (featureValue >= 4) {
            return new CohortAnalysisBehaviorPatternResponseDto(
                    "상관관계 중간",
                    "시청률이 높을 수록 구독을 더 하는 경향성이 어느 정도는 있습니다."
            );
        } else {
            return new CohortAnalysisBehaviorPatternResponseDto(
                    "상관관계 낮음",
                    "시청률이 높을 수록 구독을 더 하는 경향성이 낮습니다."
            );
        }
    }
}
