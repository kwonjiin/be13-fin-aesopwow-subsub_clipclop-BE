package com.aesopwow.subsubclipclop.domain.analysis.service.insight;

import com.aesopwow.subsubclipclop.domain.analysis.dto.insight.CohortAnalysisInsightAnalysisResultDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.insight.CohortAnalysisInsightRequestDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.insight.CohortAnalysisInsightResponseDto;
import com.aesopwow.subsubclipclop.domain.analysis.repository.insight.CohortAnalysisInsightRepository;
import com.aesopwow.subsubclipclop.domain.analysis.repository.insight.CohortAnalysisInsightJpaRepository;
import com.aesopwow.subsubclipclop.domain.analysis.repository.insight.CohortAnalysisInsightCompanyJpaRepository;
import com.aesopwow.subsubclipclop.domain.analysis.repository.insight.CohortAnalysisInsightRequestJpaRepository;
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
public class CohortAnalysisInsightServiceImpl implements CohortAnalysisInsightService {
    private final CohortAnalysisInsightRepository insightRepository;
    private final CohortAnalysisInsightJpaRepository insightJpaRepository;
    private final CohortAnalysisInsightCompanyJpaRepository insightCompanyJpaRepository;
    private final CohortAnalysisInsightRequestJpaRepository insightRequestJpaRepository;

    @Override
    @Transactional
    public CohortAnalysisInsightResponseDto fetchInsight(CohortAnalysisInsightRequestDto requestDto) {
        // 1. Python 결과 수신 (DTO 기반)
        CohortAnalysisInsightAnalysisResultDto result = insightRepository.requestInsight();
        int score = result.getScore();

        // 2. Analysis, Company 조회
        Analysis analysis = insightJpaRepository.findById((byte) 2)
                .orElseThrow(() -> new CustomException(ErrorCode.ANALYSIS_NOT_FOUND));

        Company company = insightCompanyJpaRepository.findById(requestDto.getCompanyNo())
                .orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));

        // 3. Request 저장
        RequestList savedRequest = RequestList.builder()
                .analysis(analysis)
                .company(company)
                .build();
        insightRequestJpaRepository.save(savedRequest);

        // 4. 응답 가공
        String title, content;
        if (score >= 8) {
            title = "인사이트 높음";
            content = "상세한 인사이트 설명이 여기에 들어갑니다.";
        } else if (score >= 4) {
            title = "인사이트 보통";
            content = "보통 수준의 인사이트 설명입니다.";
        } else {
            title = "인사이트 낮음";
            content = "별다른 인사이트가 감지되지 않았습니다.";
        }

        return new CohortAnalysisInsightResponseDto(title, content);
    }
}
