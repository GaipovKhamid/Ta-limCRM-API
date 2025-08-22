package com.example.demo.teachers;

import com.example.demo.group.GroupEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@Table(name = "teachers_table")
public class TeacherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName; // Полное имя преподавателя

    @Column(unique = true)
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private LocalDate dateOfBirth;

    @Column
    private String experience; // Опыт работы, например "5 лет"

    @Column
    private String education; // Образование, например "Бакалавр информатики"

    @OneToMany(mappedBy = "teacherEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupEntity> groups = new ArrayList<>(); // Группы, которые ведет преподаватель
}