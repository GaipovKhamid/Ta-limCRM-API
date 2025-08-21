package com.gaipov.TalimCRM_API.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    final AuthRepository authRepository;

    public AuthEntity register(AuthDto dto) {
        AuthEntity entity = new AuthEntity();
        entity.setFullName(dto.getFullName());
        entity.setPhoneNum(dto.getPhoneNum());
        entity.setPassword(dto.getPassword());
        entity.setRoles(dto.getRoles());
        entity.setCreatedAt(LocalDateTime.now());

        authRepository.save(entity);
        dto.setId(dto.getId());

        log.info("User successfully created.");
        return entity;
    }
}
