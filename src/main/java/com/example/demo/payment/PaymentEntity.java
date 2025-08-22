package com.example.demo.payment;

import com.example.demo.auth.AuthEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private AuthEntity student;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @CreatedDate
    private LocalDateTime createdDate;
}
