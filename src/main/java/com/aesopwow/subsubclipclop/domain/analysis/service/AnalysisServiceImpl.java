// domain/analysis/service/AnalysisServiceImpl.java
package com.aesopwow.subsubclipclop.domain.analysis.service;

import com.aesopwow.subsubclipclop.domain.analysis.dto.CohortAnalysisBehaviorPatternRequestDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.CohortAnalysisBehaviorPatternResponseDto;
import com.aesopwow.subsubclipclop.domain.analysis.repository.AnalysisRepository;
import com.aesopwow.subsubclipclop.domain.analysis.repository.AnalysisJpaRepository;
import com.aesopwow.subsubclipclop.domain.analysis.repository.CompanyJpaRepository;
import com.aesopwow.subsubclipclop.domain.analysis.repository.RequestJpaRepository;
import com.aesopwow.subsubclipclop.entity.Analysis;
import com.aesopwow.subsubclipclop.entity.Company;
import com.aesopwow.subsubclipclop.entity.RequestList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final AnalysisJpaRepository analysisJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;
    private final RequestJpaRepository requestJpaRepository;

    @Override
    @Transactional
    public CohortAnalysisBehaviorPatternResponseDto fetchBehaviorPattern(CohortAnalysisBehaviorPatternRequestDto requestDto) {
        // 1. Python 서버에서 features 수신
        Integer featureValue = analysisRepository.requestBehaviorPattern();

        // 2. Analysis 엔티티 조회
        Analysis analysis = analysisJpaRepository.findById((byte) 1)
                .orElseThrow(() -> new RuntimeException("Analysis not found"));

        // 3. Company 엔티티 조회
        Company company = companyJpaRepository.findById(requestDto.getCompanyNo())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // 4. Request 저장
        RequestList savedRequestList = RequestList.builder()
                .analysis(analysis)
                .company(company)
                .build();
        requestJpaRepository.save(savedRequestList);

        // 5. features 수치 해석해서 응답 만들기
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
                    "상관관계 낮은",
                    "시청률이 높을 수록 구독을 더 하는 경향성이 낮습니다."
            );
        }
    }
}