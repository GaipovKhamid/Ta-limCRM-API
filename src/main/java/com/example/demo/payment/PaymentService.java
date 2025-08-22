package com.example.demo.payment;

import com.example.demo.auth.AuthEntity;
import com.example.demo.auth.AuthRepository;
import com.example.demo.base.exps.BadExps;
import com.example.demo.base.exps.NotFoundExps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AuthRepository authRepository; // Для связи со студентом (AuthEntity)

    // Получить все платежи
    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Получить платеж по ID
    public Optional<PaymentDto> getPaymentById(Long id) {
        return paymentRepository.findById(id).map(this::toDto);
    }

    // Создать новый платеж
    public PaymentDto createPayment(PaymentDto paymentDto) {
        if (paymentDto.getStudent() == null || paymentDto.getAmount() == null || paymentDto.getType() == null) {
            throw new BadExps("Необходимо указать ID студента, сумму и тип платежа.");
        }

        Optional<AuthEntity> studentOptional = authRepository.findById(paymentDto.getStudent());
        if (studentOptional.isEmpty()) {
            throw new NotFoundExps("Студент с ID " + paymentDto.getStudent() + " не найден.");
        }

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setStudent(studentOptional.get());
        paymentEntity.setAmount(paymentDto.getAmount());
        paymentEntity.setType(paymentDto.getType());
        paymentEntity.setCreatedDate(LocalDateTime.now()); // Устанавливаем текущее время

        PaymentEntity savedEntity = paymentRepository.save(paymentEntity);
        return toDto(savedEntity);
    }

    // Удалить платеж (физическое удаление или логическое, пометкой)
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new NotFoundExps("Платеж с ID " + id + " не найден для удаления.");
        }
        paymentRepository.deleteById(id);
    }

    // Вспомогательный метод для конвертации Entity в DTO
    private PaymentDto toDto(PaymentEntity entity) {
        PaymentDto dto = new PaymentDto();

        dto.setId(entity.getId());
        dto.setStudent(entity.getStudent().getId() != null ? entity.getStudent().getId() : null);
        dto.setAmount(entity.getAmount());
        dto.setType(entity.getType());

        return dto;
    }
}