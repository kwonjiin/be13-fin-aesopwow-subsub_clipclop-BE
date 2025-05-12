package com.aesopwow.subsubclipclop.domain.membership.service;

import com.aesopwow.subsubclipclop.domain.membership.dto.MembershipResponseDto;

import java.util.List;

public interface MembershipService {
    List<MembershipResponseDto> getAllMembership();

    MembershipResponseDto getOneMembershipByMembershipNo(Byte membershipNo);
}
