package com.aesopwow.subsubclipclop.domain.user.service;

import com.aesopwow.subsubclipclop.domain.role.repository.RoleRepository;
import com.aesopwow.subsubclipclop.domain.user.dto.UserUpdateRequestDTO;
import com.aesopwow.subsubclipclop.domain.user.repository.UserRepository;
import com.aesopwow.subsubclipclop.entity.Role;
import com.aesopwow.subsubclipclop.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void updateUser(Long userNo, UserUpdateRequestDTO userUpdateRequestDTO) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new RuntimeException("클라이언트 정보를 찾을 수 없습니다."));

        if (userUpdateRequestDTO.getUsername() != null) {
            user.setUsername(userUpdateRequestDTO.getUsername());
        }
//        if (userUpdateRequestDTO.getUsername() != null)user.setUsername(userUpdateRequestDTO.getUsername());
//        if (userUpdateRequestDTO.getDepartmentName() != null) user.setDepartmentName(userUpdateRequestDTO.getDepartmentName());
//        if (userUpdateRequestDTO.getPassword() != null) user.setPassword(passwordEncoder.encode(userUpdateRequestDTO.getPassword()));

        userRepository.save(user);
    }

    // 직원 추가
    public void addStaff(Long adminUserNo, String staffEmail) {
        User admin = userRepository.findByUserNo(adminUserNo)
                .orElseThrow(() -> new EntityNotFoundException("관리자 정보를 찾을 수 없습니다."));

        if (admin.getRole().getName() != Role.RoleType.CLIENT_ADMIN) {
            throw new RuntimeException("직원 추가 권한이 없습니다.");
        }

        // 해당 이메일 유저가 존재하는지 확인
        User staff = userRepository.findByEmail(staffEmail)
                .orElseThrow(() -> new RuntimeException("해당 이메일 유저가 존재하지 않습니다."));

        // 이미 같은 회사 + 직원인 유저가 존재하는지 중복 확인
        if (staff.getCompany() != null &&
                staff.getCompany().equals(admin.getCompany()) &&
                staff.getRole().getName() == Role.RoleType.CLIENT_USER) {
            throw new RuntimeException("이미 해당 직원이 추가되어 있습니다.");
        }

        // 직원으로 지정 (회사, 권한 설정)
        staff.setCompany(admin.getCompany());
        staff.setRole(roleRepository.findByName(Role.RoleType.CLIENT_USER).orElseThrow());

        int maxStaff = admin.getPlanType().getMaxStaff(); // PlanType에서 직접 제한 확인

        int currentCount = userRepository.countByCompanyAndRole_Name(
                admin.getCompany(),
                Role.RoleType.CLIENT_USER
        );

        if (currentCount >= maxStaff) {
            throw new RuntimeException("직원 등록 한도를 초과했습니다.");
        }

        staff.setCompany(admin.getCompany());
        staff.setRole(roleRepository.findByName(Role.RoleType.CLIENT_USER).orElseThrow());

        userRepository.save(staff);
    }

    // 직원 조회
    public List<User> getStaffList(Long adminUserNo) {
        User admin = userRepository.findByUserNo(adminUserNo)
                .orElseThrow(() -> new EntityNotFoundException("관리자 정보를 찾을 수 없습니다."));

        if (admin.getRole().getName() != Role.RoleType.CLIENT_ADMIN) {
            throw new RuntimeException("직원 조회 권한이 없습니다.");
        }

        return userRepository.findByCompanyAndRole_NameAndIsDeletedFalse(
                admin.getCompany(),
                Role.RoleType.CLIENT_USER
        );
    }

    // 직원 삭제
    public void deleteStaff(Long userNo) {
        User user = userRepository.findByUserNo(userNo)
                .orElseThrow(() -> new EntityNotFoundException("직원 정보를 찾을 수 없습니다."));

        if (user.getRole().getName() != Role.RoleType.CLIENT_USER) {
            throw new RuntimeException("직원만 삭제할 수 있습니다.");
        }

//        user.setIsDeleted(true);
//        user 테이블에서 company_no 가 null로
        user.setCompany(null);

        userRepository.save(user);
    }
}

