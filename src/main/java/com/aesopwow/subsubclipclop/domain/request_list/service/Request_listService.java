package com.aesopwow.subsubclipclop.domain.request_list.service;

import com.aesopwow.subsubclipclop.domain.request_list.dto.Request_listRequestDto;
import com.aesopwow.subsubclipclop.domain.request_list.dto.Request_listResponseDto;

public interface Request_listService {
    public Request_listResponseDto getRequest_list(Long request_list_no);

    public Request_listResponseDto createRequest_list(Request_listRequestDto request_listRequestDto);
}
