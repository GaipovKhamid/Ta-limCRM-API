package com.example.demo.teachers;

import com.example.demo.base.exps.BadExps; // Если используешь свои исключения
import com.example.demo.base.exps.NotFoundExps; // Если используешь свои исключения
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService { // Обрати внимание, что GroupController использует TeacherService

    @Autowired
    private TeacherRepository teacherRepository;

    // Получить всех преподавателей
    public List<TeacherEntity> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // Получить преподавателя по ID
    public Optional<TeacherEntity> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    // Создать нового преподавателя
    public TeacherEntity createTeacher(TeacherDto teacherDto) {
        // Проверяем уникальность email, если это необходимо
        if (teacherDto.getEmail() != null && teacherRepository.findByEmail(teacherDto.getEmail()).isPresent()) {
            throw new BadExps("Преподаватель с таким Email уже существует.");
        }
        TeacherEntity teacherEntity = new TeacherEntity();
        mapDtoToEntity(teacherDto, teacherEntity);
        return teacherRepository.save(teacherEntity);
    }

    // Обновить существующего преподавателя
    public TeacherEntity updateTeacher(Long id, TeacherDto teacherDto) {
        Optional<TeacherEntity> existingTeacherOptional = teacherRepository.findById(id);
        if (existingTeacherOptional.isPresent()) {
            TeacherEntity existingTeacher = existingTeacherOptional.get();

            // Проверяем уникальность Email при обновлении (если Email изменен)
            if (teacherDto.getEmail() != null && !teacherDto.getEmail().equals(existingTeacher.getEmail())) {
                if (teacherRepository.findByEmail(teacherDto.getEmail()).isPresent()) {
                    throw new BadExps("Преподаватель с таким Email уже существует.");
                }
            }

            mapDtoToEntity(teacherDto, existingTeacher);
            existingTeacher.setId(id); // Убедимся, что ID сохранен
            return teacherRepository.save(existingTeacher);
        } else {
            throw new NotFoundExps("Преподаватель с ID " + id + " не найден для обновления.");
        }
    }

    // Удалить преподавателя по ID
    public void deleteTeacher(Long id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
        } else {
            throw new NotFoundExps("Преподаватель с ID " + id + " не найден для удаления.");
        }
    }

    // Вспомогательный метод для маппинга DTO в Entity
    private void mapDtoToEntity(TeacherDto teacherDto, TeacherEntity teacherEntity) {
        teacherEntity.setFullName(teacherDto.getFullName());
        teacherEntity.setEmail(teacherDto.getEmail());
        teacherEntity.setPhoneNumber(teacherDto.getPhoneNumber());
        teacherEntity.setDateOfBirth(teacherDto.getDateOfBirth());
        teacherEntity.setExperience(teacherDto.getExperience());
        teacherEntity.setEducation(teacherDto.getEducation());
    }

    // Метод для поиска по полному имени, если все еще нужен для каких-то других целей
    public Optional<TeacherEntity> findTeacherByFullName(String fullName) {
        // Этот метод будет возвращать Optional<TeacherEntity>, если точно ожидается один результат
        // Если могут быть дубликаты, лучше вернуть List<TeacherEntity> и обрабатывать их
        // Например: return teacherRepository.findByFullName(fullName).stream().findFirst();
        return teacherRepository.findByFullName(fullName); // Предполагая, что такой метод есть в репозитории
    }
}