package com.aesopwow.subsubclipclop.domain.require_list.service;

import com.aesopwow.subsubclipclop.domain.require_list.dto.RequireListRequestDto;
import com.aesopwow.subsubclipclop.domain.require_list.dto.RequireListResponseDto;

public interface RequireListService {
    public RequireListResponseDto getRequireList(Long requireListNo);

    public RequireListResponseDto createRequireList(RequireListRequestDto requireListRequestDto);
}
