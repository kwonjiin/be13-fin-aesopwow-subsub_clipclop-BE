package com.aesopwow.subsubclipclop.domain.dbinfo.service;

import com.aesopwow.subsubclipclop.domain.dbinfo.dto.DbInfoResponseDto;
import com.aesopwow.subsubclipclop.domain.dbinfo.exception.DbInfoNotFoundException;
import com.aesopwow.subsubclipclop.domain.dbinfo.repository.DbInfoRepository;
import com.aesopwow.subsubclipclop.entity.DbInfo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbInfoServiceImpl implements DbInfoService {
    private final DbInfoRepository dbInfoRepository;

    @Override
    public DbInfoResponseDto getDbInfo(Long dbInfoId) {
        DbInfo dbInfo = dbInfoRepository.findById(dbInfoId)
                .orElseThrow(() -> new DbInfoNotFoundException("해당 ID의 DB 정보를 찾을 수 없습니다: " + dbInfoId));

        return DbInfoResponseDto.builder()
                .dbInfoNo(dbInfo.getDbInfoNo())
                .companyNo(dbInfo.getCompany().getCompanyNo())
                .name(dbInfo.getName())
                .createdAt(dbInfo.getCreatedAt())
                .updatedAt(dbInfo.getUpdatedAt())
                .build();
    }
}
