package com.gaipov.TalimCRM_API.auth;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<AuthEntity, Long> {
    Optional<AuthEntity> findByFullName(String username);

    Optional<AuthEntity> findByPhoneNum(String phone);
}
