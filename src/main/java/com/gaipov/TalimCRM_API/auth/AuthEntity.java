package com.gaipov.TalimCRM_API.auth;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Builder
public class AuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String phoneNum;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole roles;

    @Column
    private LocalDateTime createdAt;
}
