package com.aesopwow.subsubclipclop.domain.analysis.service.remainheatmap;

import com.aesopwow.subsubclipclop.domain.analysis.dto.remainheatmap.CohortAnalysisRemainHeatmapRequestDto;
import com.aesopwow.subsubclipclop.domain.analysis.dto.remainheatmap.CohortAnalysisRemainHeatmapResponseDto;
import com.aesopwow.subsubclipclop.domain.analysis.repository.remainheatmap.CohortAnalysisRemainHeatmapCompanyJpaRepository;
import com.aesopwow.subsubclipclop.domain.analysis.repository.remainheatmap.CohortAnalysisRemainHeatmapJpaRepository;
import com.aesopwow.subsubclipclop.domain.analysis.repository.remainheatmap.CohortAnalysisRemainHeatmapRepository;
import com.aesopwow.subsubclipclop.domain.analysis.repository.remainheatmap.CohortAnalysisRemainHeatmapRequestJpaRepository;
import com.aesopwow.subsubclipclop.entity.Analysis;
import com.aesopwow.subsubclipclop.entity.Company;
import com.aesopwow.subsubclipclop.entity.RequestList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class CohortAnalysisRemainHeatmapServiceImpl implements CohortAnalysisRemainHeatmapService {

    private final CohortAnalysisRemainHeatmapRepository heatmapRepository;
    private final CohortAnalysisRemainHeatmapJpaRepository analysisJpaRepository;
    private final CohortAnalysisRemainHeatmapCompanyJpaRepository companyJpaRepository;
    private final CohortAnalysisRemainHeatmapRequestJpaRepository requestJpaRepository;

    @Override
    @Transactional
    public CohortAnalysisRemainHeatmapResponseDto fetchRemainHeatmap(CohortAnalysisRemainHeatmapRequestDto requestDto) {

        // 1. Python 서버 요청 및 응답 수신 (여기선 mock)
        String imageBase64 = heatmapRepository.requestHeatmapImage().getHeatmapBase64();

        // 2. 분석 유형 = 1번 (가정)
        Analysis analysis = analysisJpaRepository.findById((byte) 1)
                .orElseThrow(() -> new RuntimeException("Analysis not found"));

        // 3. 회사 정보 조회
        Company company = companyJpaRepository.findById(requestDto.getCompanyNo())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // 4. 요청 저장
        RequestList request = RequestList.builder()
                .analysis(analysis)
                .company(company)
                .build();
        requestJpaRepository.save(request);

        // 5. 응답 구성
        return new CohortAnalysisRemainHeatmapResponseDto(
                "리텐션 히트맵 분석",
                "히트맵 이미지를 통해 잔존율 분석 결과를 확인하세요.",
                imageBase64
        );
    }
}
