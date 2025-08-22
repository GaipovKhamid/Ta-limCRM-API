package com.example.demo.group;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GroupDto {
    private Long id; // Для редактирования
    private String groupName;
    private Long teacherId; // Используем ID преподавателя для надежности
    private String schedule;
    private BigDecimal price;
    private LevelOfGroups level;
    private Integer max_students;
}