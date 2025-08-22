package com.example.demo.teachers;

import com.example.demo.base.exps.BadExps;
import com.example.demo.base.exps.NotFoundExps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // Отображает список всех преподавателей
    @GetMapping("/list")
    public String listTeachers(Model model) {
        List<TeacherEntity> teachers = teacherService.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "teachers"; // Имя HTML-шаблона (teachers.html)
    }

    // Отображает форму для добавления нового преподавателя
    @GetMapping("/add")
    public String showAddTeacherForm(Model model) {
        model.addAttribute("teacher", new TeacherDto());
        return "addTeacher"; // Имя HTML-шаблона (addTeacher.html)
    }

    // Обрабатывает отправку формы для создания/обновления преподавателя
    @PostMapping("/add")
    public String createTeacher(@ModelAttribute("teacher") TeacherDto teacherDto, RedirectAttributes redirectAttributes) {
        try {
            if (teacherDto.getId() == null) {
                // Создание нового преподавателя
                teacherService.createTeacher(teacherDto);
                redirectAttributes.addFlashAttribute("successMessage", "Преподаватель успешно добавлен!");
            } else {
                // Обновление существующего преподавателя
                teacherService.updateTeacher(teacherDto.getId(), teacherDto);
                redirectAttributes.addFlashAttribute("successMessage", "Преподаватель успешно обновлен!");
            }
        } catch (BadExps | NotFoundExps e) { // Обрабатываем оба типа исключений
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            // Возвращаем на форму в случае ошибки
            if (teacherDto.getId() == null) {
                return "redirect:/teachers/add";
            } else {
                return "redirect:/teachers/edit/" + teacherDto.getId();
            }
        }
        return "redirect:/teachers/list"; // Перенаправление на список преподавателей после успеха
    }

    // Отображает форму для редактирования существующего преподавателя
    @GetMapping("/edit/{id}")
    public String showEditTeacherForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<TeacherEntity> teacherOptional = teacherService.getTeacherById(id);
        if (teacherOptional.isPresent()) {
            TeacherEntity teacherEntity = teacherOptional.get();
            TeacherDto teacherDto = new TeacherDto();
            // Маппинг Entity в DTO для заполнения формы
            teacherDto.setId(teacherEntity.getId());
            teacherDto.setFullName(teacherEntity.getFullName());
            teacherDto.setEmail(teacherEntity.getEmail());
            teacherDto.setPhoneNumber(teacherEntity.getPhoneNumber());
            teacherDto.setDateOfBirth(teacherEntity.getDateOfBirth());
            teacherDto.setExperience(teacherEntity.getExperience());
            teacherDto.setEducation(teacherEntity.getEducation());

            model.addAttribute("teacher", teacherDto);
            return "addTeacher"; // Используем ту же форму addTeacher.html для редактирования
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Преподаватель не найден для редактирования.");
            return "redirect:/teachers/list";
        }
    }

    // Обрабатывает удаление преподавателя
    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            teacherService.deleteTeacher(id);
            redirectAttributes.addFlashAttribute("successMessage", "Преподаватель успешно удален!");
        } catch (NotFoundExps e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/teachers/list"; // Перенаправление на список преподавателей
    }
}