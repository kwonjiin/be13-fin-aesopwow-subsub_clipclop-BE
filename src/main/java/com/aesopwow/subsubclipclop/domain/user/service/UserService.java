package com.aesopwow.subsubclipclop.domain.user.service;

import com.aesopwow.subsubclipclop.domain.user.dto.UserUpdateRequestDTO;
import com.aesopwow.subsubclipclop.entity.User;

import java.util.List;

public interface UserService {
    void updateUser(Long userNo, UserUpdateRequestDTO userUpdateRequestDTO);

    List<User> getStaffList(Long adminUserNo);

    void addStaff(Long adminUserNo, String staffEmail);

    void deleteStaff(Long userNo);
}