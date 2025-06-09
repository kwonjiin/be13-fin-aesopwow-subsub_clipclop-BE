package com.aesopwow.subsubclipclop.domain.user.service;

import com.aesopwow.subsubclipclop.domain.user.dto.PasswordChangeRequestDTO;
import com.aesopwow.subsubclipclop.domain.user.dto.UserDeleteRequestDto;
import com.aesopwow.subsubclipclop.domain.user.dto.UserResponseDTO;
import com.aesopwow.subsubclipclop.domain.user.dto.UserUpdateRequestDTO;
import com.aesopwow.subsubclipclop.entity.User;

import java.util.List;

public interface UserService {
    void updateUser(Long userNo, UserUpdateRequestDTO userUpdateRequestDTO);

    List<User> getStaffList(Long adminUserNo);

    void addStaff(Long adminUserNo, String staffEmail);

    void deleteStaff(Long userNo);

    void updateUserIs_deleted(Long userNo, UserDeleteRequestDto userDeleteRequestDto);

    void changePassword(Long userNo, PasswordChangeRequestDTO passwordChangeRequestDTO);

    User getOneUserByUserNo(Long userNo);

    String getRoleNameByRoleNo(Long roleNo);

    String getOriginTableByInfoDbNo(Long infoDbNo);
}
