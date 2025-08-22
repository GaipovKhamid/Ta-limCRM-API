package com.example.demo.auth;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
public class AuthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true, length = 20    )
    private String telephone;

    @Column(length = 31,  nullable = false)
    private Integer testScore;

    @CreatedDate
    private LocalDateTime createTime;

    private LocalDateTime deleteTime;

}
