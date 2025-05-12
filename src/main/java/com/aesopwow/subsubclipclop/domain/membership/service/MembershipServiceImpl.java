package com.aesopwow.subsubclipclop.domain.membership.service;

import com.aesopwow.subsubclipclop.domain.membership.dto.MembershipResponseDto;
import com.aesopwow.subsubclipclop.domain.membership.repository.MembershipRepository;
import com.aesopwow.subsubclipclop.entity.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService{
    private final MembershipRepository membershipRepository;

    @Override
    public List<MembershipResponseDto> getAllMembership() {
        List<MembershipResponseDto> membershipResponseDtos
                = membershipRepository.findAll()
                .stream()
                .map(MembershipResponseDto::new)
                .toList();

        return membershipResponseDtos;
    }

    @Override
    public MembershipResponseDto getOneMembershipByMembershipNo(Byte membershipNo) {
        Membership membership
                = membershipRepository.findById(Long.valueOf(membershipNo))
                    .orElseThrow(() ->  new RuntimeException("멤버십을 찾을 수 없습니다."));

        return new MembershipResponseDto(membership);
    }

}
