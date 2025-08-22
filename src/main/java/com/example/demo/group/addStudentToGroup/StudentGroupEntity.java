package com.example.demo.group.addStudentToGroup;

import com.example.demo.auth.AuthEntity;
import com.example.demo.group.GroupEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

@Data
@Entity
public class StudentGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private AuthEntity studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    private GroupEntity groupId;
}
