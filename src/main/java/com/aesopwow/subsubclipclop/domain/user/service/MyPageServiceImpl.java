package com.aesopwow.subsubclipclop.domain.user.service;


import com.aesopwow.subsubclipclop.domain.company.dto.CompanyUpdateRequestDTO;
import com.aesopwow.subsubclipclop.domain.company.repository.CompanyRepository;
import com.aesopwow.subsubclipclop.domain.membership.repository.MembershipRepository;
import com.aesopwow.subsubclipclop.domain.user.dto.MyPageResponseDTO;
import com.aesopwow.subsubclipclop.domain.user.dto.MyPageUpdateRequestDTO;
import com.aesopwow.subsubclipclop.domain.user.repository.UserRepository;
import com.aesopwow.subsubclipclop.entity.Company;
import com.aesopwow.subsubclipclop.entity.Membership;
import com.aesopwow.subsubclipclop.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {
    private final UserRepository userRepository;

    @Override
    public MyPageResponseDTO getMyPageInfo(Long userNo) {
        User user = userRepository.findByUserNo(userNo)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Company company = user.getCompany();
        if (company == null) {
            throw new RuntimeException("사용자에게 연결된 회사 정보가 없습니다.");
        }

        Membership membership = company.getMembership();
        if (membership == null) {
            throw new RuntimeException("회사의 멤버십 정보가 없습니다.");
        }

        return MyPageResponseDTO.builder()
                .userNo(user.getUserNo())
                .username(user.getUsername())
                .membershipName(membership.getName())
//                .departmentName(company.getDepartmentName())
                .build();
    }

    @Override
    public MyPageResponseDTO updateMyPageInfo(MyPageUpdateRequestDTO myPageUpdateRequestDTO) {
        User user = userRepository.findByUserNo(myPageUpdateRequestDTO.getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        if (myPageUpdateRequestDTO.getUsername() != null) {
            user.setUsername(myPageUpdateRequestDTO.getUsername());
        }

        userRepository.save(user);

        return MyPageResponseDTO.builder()
                .userNo(user.getUserNo())
                .username(user.getUsername())
                .membershipName("미지정") // 필요한 경우 membership 조회
                .build();
    }

    @Override
    public void updateCompanyInfo(CompanyUpdateRequestDTO companyUpdateRequestDTO) {
        // TODO: 회사 정보 업데이트 구현
    }
}
