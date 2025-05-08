package com.aesopwow.subsubclipclop.domain.dbinfo.service;

import com.aesopwow.subsubclipclop.domain.dbinfo.dto.DbInfoResponseDto;
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
                .orElseThrow(() -> new EntityNotFoundException("DB 정보가 존재하지 않습니다."));

        return DbInfoResponseDto.builder()
                .dbInfoNo(dbInfo.getDbInfoNo())
                .companyNo(dbInfo.getCompany().getCompanyNo())
                .name(dbInfo.getName())
                .createdAt(dbInfo.getCreatedAt())
                .updatedAt(dbInfo.getUpdatedAt())
                .build();
    }
}
