package com.aesopwow.subsubclipclop.domain.dbinfo.service;

import com.aesopwow.subsubclipclop.domain.dbinfo.dto.DbInfoResponseDto;

public interface DbInfoService {
    DbInfoResponseDto getDbInfo(Long dbInfoNo);
}
