package com.example.demo.group.addStudentToGroup;

import com.example.demo.auth.AuthEntity;
import com.example.demo.group.GroupEntity;
import lombok.Data;

@Data
public class StudentGroupDto {
    private Long id;
    private AuthEntity studentId;
    private GroupEntity groupId;
}
