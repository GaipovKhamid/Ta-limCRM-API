package com.example.demo.student;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {
    final StudentRepository repository;

    public StudentDto createStudent(StudentDto dto) {
        StudentEntity entity = new StudentEntity();
        entity.setFullName(dto.getFullName());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        entity.setDate_of_birth(dto.getDate_of_birth());
        entity.setStatus(StudentStatus.PENDING);
        entity.setCreated_at(LocalDate.now());
        repository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public List<StudentDto> getAllStudents() {
        List<StudentDto> dtos = new ArrayList<>();
        for (StudentEntity entity : repository.findAll()) {
            StudentDto studentDto = new StudentDto();
            studentDto.setId(entity.getId());
            studentDto.setFullName(entity.getFullName());
            studentDto.setStatus(entity.getStatus());
            studentDto.setAddress(entity.getAddress());
            studentDto.setPhone(entity.getPhone());
            studentDto.setDate_of_birth(entity.getDate_of_birth());
            studentDto.setCreated_at(entity.getCreated_at());
            dtos.add(studentDto);
        }
        return dtos;
    }
}
