package com.example.demo.teachers;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TeacherDto {
    private Long id; // Для редактирования
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String experience;
    private String education;
}