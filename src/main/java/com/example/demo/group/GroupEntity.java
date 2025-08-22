package com.example.demo.group;

import com.example.demo.auth.AuthEntity;
import com.example.demo.teachers.TeacherEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "groups_table") // Хорошая практика, чтобы избежать конфликтов с ключевыми словами
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String groupName;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER обычно подходит для ManyToOne
    @JoinColumn(name = "teacher_id") // Имя колонки внешнего ключа в таблице groups_table
    private TeacherEntity teacherEntity; // Правильное имя поля

    @Column
    private String schedule;

    private BigDecimal price;

    @ManyToMany(fetch = FetchType.LAZY) // LAZY обычно лучше для ManyToMany
    @JoinTable(
            name = "group_students",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<AuthEntity> students = new ArrayList<>(); // Инициализация списка

    @Enumerated(EnumType.STRING)
    private LevelOfGroups level;

    private Integer max_students = 30; // Дефолтное значение
}