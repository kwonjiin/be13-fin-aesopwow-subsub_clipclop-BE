package com.aesopwow.subsubclipclop.domain.requirelist.service;

import com.aesopwow.subsubclipclop.domain.requirelist.dto.RequireListRequestDto;
import com.aesopwow.subsubclipclop.domain.requirelist.dto.RequireListResponseDto;

public interface RequireListService {
    public RequireListResponseDto getRequireList(Long requireListNo);

    public RequireListResponseDto createRequireList(RequireListRequestDto requireListRequestDto);
}
