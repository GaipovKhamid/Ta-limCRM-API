package com.gaipov.TalimCRM_API.auth;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class AuthDto {
    private Long id;
    private String fullName;
    private String phoneNum;
    private String password;
    private UserRole roles;
}
