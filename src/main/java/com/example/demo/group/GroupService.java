package com.example.demo.group;

import com.example.demo.auth.AuthEntity;
import com.example.demo.auth.AuthRepository;
import com.example.demo.teachers.TeacherEntity;
import com.example.demo.teachers.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TeacherRepository teacherRepository; // Нужен для поиска TeacherEntity

    @Autowired
    private AuthRepository authRepository;

    // Получить все группы
    public List<GroupEntity> getAllGroups() {
        return groupRepository.findAll();
    }

    // Получить группу по ID
    public Optional<GroupEntity> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    // Создать новую группу
    public GroupEntity createGroup(GroupDto groupDto) {
        GroupEntity groupEntity = new GroupEntity();
        mapDtoToEntity(groupDto, groupEntity); // Использование вспомогательного метода
        return groupRepository.save(groupEntity);
    }

    // Обновить существующую группу
    public GroupEntity updateGroup(Long id, GroupDto groupDto) {
        Optional<GroupEntity> existingGroupOptional = groupRepository.findById(id);
        if (existingGroupOptional.isPresent()) {
            GroupEntity existingGroup = existingGroupOptional.get();
            mapDtoToEntity(groupDto, existingGroup); // Использование вспомогательного метода
            existingGroup.setId(id); // Убедимся, что ID сохранен
            return groupRepository.save(existingGroup);
        } else {
            throw new IllegalArgumentException("Группа с ID " + id + " не найдена для обновления.");
        }
    }

    // Удалить группу по ID
    public void deleteGroup(Long id) {
        if (groupRepository.existsById(id)) {
            groupRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Группа с ID " + id + " не найдена для удаления.");
        }
    }

    // Вспомогательный метод для маппинга DTO в Entity
    private void mapDtoToEntity(GroupDto groupDto, GroupEntity groupEntity) {
        groupEntity.setGroupName(groupDto.getGroupName());
        groupEntity.setLevel(groupDto.getLevel());
        groupEntity.setSchedule(groupDto.getSchedule());
        groupEntity.setPrice(groupDto.getPrice());

        // Устанавливаем max_students только если оно указано в DTO, иначе используем дефолтное значение из Entity
        if (groupDto.getMax_students() != null) {
            groupEntity.setMax_students(groupDto.getMax_students());
        } else {
            // Если в DTO null, и entity уже существует, это может обнулить значение.
            // Для новой группы default 30 сработает. Для обновления нужно быть внимательным.
            // Если max_students не пришел в DTO, оставляем его текущее значение в entity.
            if (groupEntity.getMax_students() == null) { // Если в entity еще нет значения (например, новая entity)
                groupEntity.setMax_students(30); // Установить дефолтное, если оно не было установлено ранее
            }
        }


        // Поиск и установка TeacherEntity по teacherId из DTO
        Long teacherId = groupDto.getTeacherId();
        if (teacherId != null) {
            Optional<TeacherEntity> teacherOptional = teacherRepository.findById(teacherId);
            if (teacherOptional.isPresent()) {
                groupEntity.setTeacherEntity(teacherOptional.get());
            } else {
                throw new IllegalArgumentException("Преподаватель с ID " + teacherId + " не найден.");
            }
        } else {
            // Если teacherId не предоставлен, устанавливаем teacherEntity в null
            // Если группа может существовать без преподавателя, это нормально.
            // Если преподаватель обязателен, здесь нужно бросить исключение.
            groupEntity.setTeacherEntity(null);
            // throw new IllegalArgumentException("Необходимо выбрать преподавателя для группы.");
        }
    }

    // Дополнительные методы фильтрации
    public List<GroupEntity> filterByLevel(LevelOfGroups level) {
        return groupRepository.findByLevel(level);
    }

    // Метод для добавления студента в группу (пока без реализации AuthRepository)
    // Тебе нужно будет инжектировать AuthRepository и использовать его.
    public void addStudentToGroup(Long groupId, Long studentId) {
        Optional<GroupEntity> groupOptional = groupRepository.findById(groupId);
        Optional<AuthEntity> studentOptional = authRepository.findById(studentId); // Тебе нужно инжектировать AuthRepository

        if (groupOptional.isPresent() && studentOptional.isPresent()) {
            GroupEntity group = groupOptional.get();
            AuthEntity student = studentOptional.get();

            if (!group.getStudents().contains(student)) { // Избегаем дубликатов
                group.getStudents().add(student);
                groupRepository.save(group);
            }
        } else {
            throw new IllegalArgumentException("Группа или студент не найдены.");
        }
    }
}