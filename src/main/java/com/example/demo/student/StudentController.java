package com.example.demo.student; // Создаем новый пакет, например, 'student'

import com.example.demo.auth.AuthDto; // Предполагаем, что AuthDto используется для данных студента
import com.example.demo.auth.AuthService; // Используем AuthService для работы с данными студентов
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List; // Для списка студентов

@Controller
@RequestMapping("/students") // Все запросы, касающиеся студентов, будут начинаться с /students
@Tag(name = "Student Controller", description = "Управление студентами учебного центра")
public class StudentController {

    @Autowired
    AuthService authService; // Используем AuthService, который содержит логику работы со студентами
    // Метод для отображения списка студентов
    // GET /students/list
    @Operation(summary = "Получить список всех студентов")
    @GetMapping("/list")
    public String listStudents(Model model) {
        // Предполагаем, что AuthService имеет метод для получения всех студентов
        // Тебе нужно добавить его в AuthService.java: public List<AuthEntity> getAllStudents() { return authRepository.findAll(); }
        List<AuthDto> students = authService.getAllStudents(); // Нужно реализовать этот метод в AuthService
        model.addAttribute("students", students);
        return "students"; // Возвращаем имя HTML-файла (students.html)
    }

    // Метод для отображения формы добавления нового студента
    // GET /students/add
    @Operation(summary = "Показать форму добавления нового студента")
    @GetMapping("/add")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new AuthDto()); // Передаем пустой объект для заполнения формы
        return "addStudent"; // Возвращаем имя HTML-файла (addStudent.html)
    }

    // Метод для обработки отправки формы добавления студента
    // POST /students/add
    @Operation(summary = "Добавить нового студента")
    @PostMapping("/add")
    public String addStudent(@ModelAttribute("student") AuthDto authDto) {
        authService.createNewUser(authDto); // Сохраняем нового студента
        return "redirect:/students/list"; // Перенаправляем на страницу со списком студентов
    }

    // Метод для обработки удаления студента
    // POST /students/delete/{id} (более безопасный способ, чем DELETE для форм)
    @Operation(summary = "Удалить студента по ID")
    @PostMapping("/delete/{id}") // Используем POST для удаления из формы
    public String deleteStudent(@PathVariable Long id) {
        authService.deleteStudent(id);
        return "redirect:/students/list"; // Перенаправляем на страницу со списком студентов
    }

    // TODO: Добавить методы для редактирования и просмотра отдельного студента:
    // @GetMapping("/{id}") // GET /students/{id}
    // public String viewStudent(@PathVariable Long id, Model model) { ... }
    // @GetMapping("/{id}/edit") // GET /students/{id}/edit
    // public String showEditStudentForm(@PathVariable Long id, Model model) { ... }
    // @PostMapping("/{id}/edit") // POST /students/{id}/edit
    // public String updateStudent(@PathVariable Long id, @ModelAttribute AuthDto authDto) { ... }
}