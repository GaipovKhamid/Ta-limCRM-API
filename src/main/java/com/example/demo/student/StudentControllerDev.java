package com.example.demo.student;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/dev")
public class StudentControllerDev {
    final StudentService service;

    @PostMapping("/create")
    public ResponseEntity<StudentDto> createSt(@RequestBody StudentDto dto) {
        return ResponseEntity.ok(service.createStudent(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> getAll() {
        return ResponseEntity.ok(service.getAllStudents());
    }
}
