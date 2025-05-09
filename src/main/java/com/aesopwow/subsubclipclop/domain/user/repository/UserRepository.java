package com.aesopwow.subsubclipclop.domain.user.repository;

import com.aesopwow.subsubclipclop.entity.Company;
import com.aesopwow.subsubclipclop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import com.aesopwow.subsubclipclop.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserNo(Long userNo);

    Optional<User> findByEmail(String email);

    // 유저 전체 목록 조회
    List<User> findByCompanyAndRole_Name(Company company, Role.RoleType roleType);

    // 유저 수 제한 체크
    int countByCompanyAndRole_Name(Company company, Role.RoleType roleType);

    List<User> findByCompanyAndRole_NameAndIsDeletedFalse(
            Company company,
            Role.RoleType roleType
    );
}
