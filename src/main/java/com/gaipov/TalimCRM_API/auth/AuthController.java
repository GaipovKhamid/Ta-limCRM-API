package com.gaipov.TalimCRM_API.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    final AuthService service;

    @PostMapping("/register")
    private ResponseEntity<AuthEntity> register(@RequestBody AuthDto dto) {
        log.info("Created successfully");
        return ResponseEntity.ok(service.register(dto));
    }
}
