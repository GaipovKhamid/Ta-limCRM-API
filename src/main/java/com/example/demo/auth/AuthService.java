package com.example.demo.auth;

import com.example.demo.base.exps.BadExps;
import com.example.demo.base.exps.NotFoundExps;
import com.example.demo.payment.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    AuthRepository authRepository;

    @Autowired
    PaymentRepository paymentRepository;

    public String createNewUser(AuthDto authDto) {
        if (authDto.getFullName() == null || authDto.getTelephone() == null) {
            throw new BadExps("You have to enter full name and telephone number");
        }
        Optional<AuthEntity> existing = authRepository.findByTelephone(authDto.getTelephone());
        if (existing.isPresent()) {
            throw new BadExps("Telephone already exists");
        }

        AuthEntity entity = new AuthEntity();
        entity.setFullName(authDto.getFullName());
        entity.setTelephone(authDto.getTelephone());
        entity.setTestScore(authDto.getTestScore());
        entity.setCreateTime(LocalDateTime.now());

        authRepository.save(entity);
        authDto.setId(entity.getId());

        return entity.getFullName() + " created successfully!";
    }

    public void deleteStudent(Long id) {
        Optional<AuthEntity> existing = authRepository.findById(id);
        if (existing.isEmpty()) {
            throw new NotFoundExps("Student not found");
        }
        paymentRepository.findByStudentId(id).forEach(payment -> {
            paymentRepository.delete(payment);
        });
        existing.get().setDeleteTime(LocalDateTime.now());
        authRepository.deleteById(existing.get().getId());
    }

    public List<AuthDto> getAllStudents() {
        List<AuthDto> authDtos = new ArrayList<>();
        for (AuthEntity entity : authRepository.findAll()) {
            AuthDto authDto = new AuthDto();
            authDto.setId(entity.getId());
            authDto.setFullName(entity.getFullName());
            authDto.setTelephone(entity.getTelephone());
            authDto.setTestScore(entity.getTestScore());
            authDtos.add(authDto);
        }
        return authDtos;
    }

    public String register() {
        // todo
        return null;
    }
}
