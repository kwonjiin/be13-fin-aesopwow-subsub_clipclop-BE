package com.aesopwow.subsubclipclop.domain.user.service;

import com.aesopwow.subsubclipclop.domain.company.repository.CompanyRepository;
import com.aesopwow.subsubclipclop.domain.membership.dto.MembershipResponseDto;
import com.aesopwow.subsubclipclop.domain.membership.service.MembershipService;
import com.aesopwow.subsubclipclop.domain.role.repository.RoleRepository;
import com.aesopwow.subsubclipclop.domain.user.dto.PasswordChangeRequestDTO;
import com.aesopwow.subsubclipclop.domain.user.dto.UserDeleteRequestDto;
import com.aesopwow.subsubclipclop.domain.user.dto.UserUpdateRequestDTO;
import com.aesopwow.subsubclipclop.domain.user.repository.InfoColumnRepository;
import com.aesopwow.subsubclipclop.domain.user.repository.UserRepository;
import com.aesopwow.subsubclipclop.entity.Role;
import com.aesopwow.subsubclipclop.entity.User;
import com.aesopwow.subsubclipclop.global.enums.ErrorCode;
import com.aesopwow.subsubclipclop.global.exception.CustomException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MembershipService membershipService;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;
    private final InfoColumnRepository infoColumnRepository;

    @Override
    @Transactional
    public void updateUser(Long userNo, UserUpdateRequestDTO userUpdateRequestDTO) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (userUpdateRequestDTO.getUserName() != null) {
            user.setName(userUpdateRequestDTO.getUserName());
        } else {
            throw new CustomException(ErrorCode.NAME_REQUIRED);
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void changePassword(Long userNo, PasswordChangeRequestDTO passwordChangeRequestDTO) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(passwordChangeRequestDTO.getOldPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        if (passwordEncoder.matches(passwordChangeRequestDTO.getNewPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.SAME_PASSWORD);
        }

        user.setPassword(passwordEncoder.encode(passwordChangeRequestDTO.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void addStaff(Long adminUserNo, String userEmail) {
        User admin = userRepository.findByUserNo(adminUserNo)
                .orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));

        User staff = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (staff.getCompany() != null &&
                staff.getCompany().equals(admin.getCompany()) &&
                staff.getRole().getName() == Role.RoleType.CLIENT_USER
        ) {
            throw new CustomException(ErrorCode.STAFF_ALREADY_EXISTS);
        }

        staff.setCompany(admin.getCompany());

        try {
            staff.setRole(roleRepository.findByName(Role.RoleType.CLIENT_USER));
        } catch (EntityNotFoundException e) {
            throw new CustomException(ErrorCode.ROLE_NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int currentCount = userRepository.countByCompanyAndRole_Name(
                admin.getCompany(),
                Role.RoleType.CLIENT_USER
        );

        byte membershipNo = admin.getCompany().getMembership().getMembershipNo();
        MembershipResponseDto membershipResponseDto = membershipService.getOneMembershipByMembershipNo(membershipNo);

        if (currentCount >= membershipResponseDto.getMaxPerson()) {
            throw new CustomException(ErrorCode.STAFF_LIMIT_EXCEEDED);
        }

        userRepository.save(staff);
    }

    @Override
    public List<User> getStaffList(Long adminUserNo) {
        User admin = userRepository.findByUserNo(adminUserNo)
                .orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));

        if (admin.getRole().getName() != Role.RoleType.CLIENT_ADMIN) {
            throw new CustomException(ErrorCode.ONLY_CLIENT_ADMIN_ALLOWED);
        }

        return userRepository.findByCompanyAndRole_NameAndIsDeletedFalse(
                admin.getCompany(),
                Role.RoleType.CLIENT_USER
        );
    }

    @Override
    public void deleteStaff(Long userNo) {
        User user = userRepository.findByUserNo(userNo)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (user.getRole().getName() != Role.RoleType.CLIENT_USER) {
            throw new CustomException(ErrorCode.ONLY_CLIENT_USER_DELETABLE);
        }

        user.setCompany(null);
        userRepository.save(user);
    }

    @Override
    public void updateUserIs_deleted(Long userNo, UserDeleteRequestDto userDeleteRequestDto) {
        User user = userRepository.findByUserNo(userNo)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.setIsDeleted(userDeleteRequestDto.getIsDeleted());
        userRepository.save(user);
    }

    @Override
    public User getOneUserByUserNo(Long userNo) {
        User user = userRepository.findByUserNo(userNo)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public String getOriginTableByInfoDbNo(Long infoDbNo) {
        return infoColumnRepository.findFirstByInfoDb_InfoDbNo(infoDbNo)
                .orElseThrow(() -> new CustomException(ErrorCode.INFO_COLUMN_NOT_FOUND))
                .getOriginTable();
    }

    @Override
    public String getRoleNameByRoleNo(Long roleNo) {
        Role role = roleRepository.findById(roleNo)
                .orElseThrow(() -> new CustomException(ErrorCode.ROLE_NOT_FOUND));
        return role.getName().name(); // enum 값을 문자열로 변환
    }
}