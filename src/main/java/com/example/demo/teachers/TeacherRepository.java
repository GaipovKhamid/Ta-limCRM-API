package com.example.demo.teachers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    Optional<TeacherEntity> findByEmail(String email);
    Optional<TeacherEntity> findByFullName(String fullName); // Если ты решил использовать это
    // List<TeacherEntity> findByFullNameContainingIgnoreCase(String fullName); // Альтернатива для поиска по части имени
}