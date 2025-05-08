package com.aesopwow.subsubclipclop.domain.dbinfo.service;

import com.aesopwow.subsubclipclop.domain.dbinfo.dto.DbInfoResponseDto;
import com.aesopwow.subsubclipclop.domain.dbinfo.repository.DbInfoRepository;
import com.aesopwow.subsubclipclop.entity.DbInfo;
import com.aesopwow.subsubclipclop.global.enums.ErrorCode;
import com.aesopwow.subsubclipclop.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbInfoServiceImpl implements DbInfoService {
    private final DbInfoRepository dbInfoRepository;

    @Override
    public DbInfoResponseDto getDbInfo(Long dbInfoId) {
        DbInfo dbInfo = dbInfoRepository.findById(dbInfoId)
                .orElseThrow(() -> new CustomException(ErrorCode.DB_INFO_NOT_FOUND));

        return DbInfoResponseDto.builder()
                .dbInfoNo(dbInfo.getDbInfoNo())
                .companyNo(dbInfo.getCompany().getCompanyNo())
                .name(dbInfo.getName())
                .createdAt(dbInfo.getCreatedAt())
                .updatedAt(dbInfo.getUpdatedAt())
                .build();
    }
}
