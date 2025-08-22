package com.example.demo.payment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDto {
    private Long id;
    private Long student;
    private BigDecimal amount;
    private PaymentType type;
}
