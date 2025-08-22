package com.example.demo.group;

import com.example.demo.teachers.TeacherService; // Предполагается, что у тебя есть TeacherService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private TeacherService teacherService; // Для получения списка преподавателей

    // Отображает список всех групп
    @GetMapping("/list")
    public String listGroups(Model model) {
        List<GroupEntity> groups = groupService.getAllGroups();
        model.addAttribute("groups", groups);
        return "groups"; // Имя файла HTML-шаблона (groups.html)
    }

    // Отображает форму для добавления новой группы
    @GetMapping("/add")
    public String showAddGroupForm(Model model) {
        model.addAttribute("group", new GroupDto());
        model.addAttribute("levels", LevelOfGroups.values()); // Для выпадающего списка уровней
        model.addAttribute("teachers", teacherService.getAllTeachers()); // Для выпадающего списка преподавателей
        return "addGroup"; // Имя файла HTML-шаблона (addGroup.html)
    }

    // Обрабатывает отправку формы для создания/обновления группы
    @PostMapping("/add")
    public String createGroup(@ModelAttribute("group") GroupDto groupDto, RedirectAttributes redirectAttributes) {
        try {
            if (groupDto.getId() == null) {
                // Создание новой группы
                groupService.createGroup(groupDto);
                redirectAttributes.addFlashAttribute("successMessage", "Группа успешно добавлена!");
            } else {
                // Обновление существующей группы
                groupService.updateGroup(groupDto.getId(), groupDto);
                redirectAttributes.addFlashAttribute("successMessage", "Группа успешно обновлена!");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            // Если ошибка, возвращаемся на форму, чтобы пользователь мог исправить
            // Если это форма добавления, вернется на /group/add
            // Если это форма редактирования, нужно будет перенаправить на /group/edit/{id}
            if (groupDto.getId() == null) {
                return "redirect:/group/add";
            } else {
                return "redirect:/group/edit/" + groupDto.getId();
            }
        }
        return "redirect:/group/list";
    }

    // Отображает форму для редактирования существующей группы
    @GetMapping("/edit/{id}")
    public String showEditGroupForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<GroupEntity> groupOptional = groupService.getGroupById(id);
        if (groupOptional.isPresent()) {
            GroupEntity groupEntity = groupOptional.get();
            GroupDto groupDto = new GroupDto();
            groupDto.setId(groupEntity.getId());
            groupDto.setGroupName(groupEntity.getGroupName());
            groupDto.setLevel(groupEntity.getLevel());
            groupDto.setSchedule(groupEntity.getSchedule());
            groupDto.setPrice(groupEntity.getPrice());
            groupDto.setMax_students(groupEntity.getMax_students());
            if (groupEntity.getTeacherEntity() != null) {
                groupDto.setTeacherId(groupEntity.getTeacherEntity().getId()); // Устанавливаем ID преподавателя
            }

            model.addAttribute("group", groupDto);
            model.addAttribute("levels", LevelOfGroups.values());
            model.addAttribute("teachers", teacherService.getAllTeachers());
            return "addGroup"; // Используем ту же форму addGroup.html для редактирования
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Группа не найдена для редактирования.");
            return "redirect:/group/list";
        }
    }

    // Обрабатывает удаление группы
    @GetMapping("/delete/{id}")
    public String deleteGroup(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            groupService.deleteGroup(id);
            redirectAttributes.addFlashAttribute("successMessage", "Группа успешно удалена!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/group/list"; // Перенаправление на список групп
    }
}