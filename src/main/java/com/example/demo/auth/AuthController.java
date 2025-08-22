package com.example.demo.auth;

import com.example.demo.base.exps.BadExps; // Для обработки BadExps
import com.example.demo.base.exps.NotFoundExps; // Для обработки NotFoundExps
import com.example.demo.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    PaymentService paymentService;
    @Autowired
    private AuthRepository authRepository;

    @GetMapping("/list")
    public String listAuthEntities(Model model) {
        List<AuthDto> auths = authService.getAllStudents();
        model.addAttribute("auths", auths);
        return "students";
    }

    @GetMapping("/add")
    public String showAddAuthForm(Model model) {
        model.addAttribute("auth", new AuthDto());
        return "addStudent";
    }

    @PostMapping("/add")
    public String createAuth(@ModelAttribute("auth") AuthDto authDto, RedirectAttributes redirectAttributes) {
        try {
            String message = authService.createNewUser(authDto);
            redirectAttributes.addFlashAttribute("successMessage", message);
        } catch (BadExps e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/auth/add";
        }
        return "redirect:/auth/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuth(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            authService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("successMessage", "Запись успешно помечена как удаленная!");
        } catch (NotFoundExps e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/auth/list";
    }

    @GetMapping("/update")
    public String showAddAuthFormForUpdate(@RequestParam("id") Long id, Model model) {
        AuthEntity entity = authRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        AuthDto dto = new AuthDto();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setTelephone(entity.getTelephone());
        dto.setTestScore(entity.getTestScore());

        model.addAttribute("auth", dto);
        return "updateStudent";
    }

}