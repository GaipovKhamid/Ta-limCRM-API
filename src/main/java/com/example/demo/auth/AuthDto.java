package com.example.demo.auth;

import lombok.Data;

@Data
public class AuthDto {
    private Long id;
    private String fullName;
    private String telephone;
    private Integer testScore;
}
