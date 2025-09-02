package com.example.demo.student;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fullName;

    @Column(unique = true)
    private String phone;

    @Column
    private String date_of_birth;

    @Column
    private String address;

    @Column
    @Enumerated(EnumType.STRING)
    private StudentStatus status;

    @Column
    private LocalDate created_at;

    @Column
    private LocalDate updated_at;
}
