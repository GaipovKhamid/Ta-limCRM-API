package com.gaipov.TalimCRM_API.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    final AuthRepository authRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthEntity register(AuthDto dto) {
        AuthEntity entity = AuthEntity.builder()
                .fullName(dto.getFullName())
                .phoneNum(dto.getPhoneNum())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .roles(dto.getRoles())
                .createdAt(LocalDateTime.now())
                .build();

        authRepository.save(entity);

        log.info("User successfully created for full name: {}", dto.getFullName());
        return entity;
    }
}
