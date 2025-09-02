package com.example.demo.student;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentDto {
    private Long id;
    private String fullName;
    private String phone;
    private String date_of_birth;
    private String address;
    private StudentStatus status;
    private LocalDate created_at;
}
