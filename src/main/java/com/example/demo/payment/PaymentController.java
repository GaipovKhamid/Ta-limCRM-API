package com.example.demo.payment;

import com.example.demo.auth.AuthService; // Чтобы иметь доступ к списку студентов (AuthEntity)
import com.example.demo.base.exps.BadExps;
import com.example.demo.base.exps.NotFoundExps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AuthService authService; // Для получения списка студентов AuthEntity

    // Отображает список всех платежей
    @GetMapping("/list")
    public String listPayments(Model model) {
        List<PaymentDto> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "payments"; // Имя файла HTML-шаблона для списка платежей
    }

    // Отображает форму для добавления нового платежа
    @GetMapping("/add")
    public String showAddPaymentForm(Model model) {
        model.addAttribute("payment", new PaymentDto());
        model.addAttribute("paymentTypes", PaymentType.values()); // Для выпадающего списка типов оплаты
        model.addAttribute("students", authService.getAllStudents()); // Список AuthEntity для выбора студента
        return "createPayment"; // Имя файла HTML-шаблона для формы добавления платежа
    }

    // Обрабатывает отправку формы для создания платежа
    @PostMapping("/add")
    public String createPayment(@ModelAttribute("payment") PaymentDto paymentDto, RedirectAttributes redirectAttributes) {
        try {
            paymentService.createPayment(paymentDto);
            redirectAttributes.addFlashAttribute("successMessage", "Платеж успешно добавлен!");
        } catch (BadExps | NotFoundExps e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/payment/add"; // В случае ошибки возвращаем на форму добавления
        }
        return "redirect:/payment/list"; // Перенаправляем на список платежей
    }

    // Эндпоинт для удаления платежа (если нужно)

    // Возможно, вам понадобится отдельная страница для просмотра деталей платежа,
    // но обычно это делается через список.
}