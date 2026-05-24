package com.practice.spring_mvc.controller;

import com.practice.spring_mvc.dto.LoginRequestDto;
import com.practice.spring_mvc.dto.UserRequestDto;
import com.practice.spring_mvc.exception.ValidationException;
import com.practice.spring_mvc.repository.RoleRepository;
import com.practice.spring_mvc.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/register")
    public String showRegisterForm(Model model, HttpSession session) {
        if (session.getAttribute("currentUser") != null) {
            return "redirect:/users";
        }
        model.addAttribute("registerDto", new UserRequestDto());
        return "auth/register"; // Trỏ đến src/main/resources/templates/auth/register.html
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerDto") UserRequestDto dto, Model model) {
        try {
            userService.createUser(dto);
            return "redirect:/login?registered=true";
        } catch (ValidationException e) {
            model.addAttribute("errors", e.getErrors());
            model.addAttribute("registerDto", dto); // Giữ lại dữ liệu cũ người dùng đã nhập
            return "auth/register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model, HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("currentUser") != null) {
            return "redirect:/users";
        }

        if (request.getParameter("registered") != null) {
            model.addAttribute("successMessage", "Đăng ký tài khoản thành công! Mời bạn đăng nhập.");
        }

        model.addAttribute("loginDto", new LoginRequestDto());
        return "auth/login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("loginDto") LoginRequestDto dto,
                              HttpServletRequest request,
                              Model model) {
        try {
            userService.login(dto, request);
            return "redirect:/users";
        } catch (RuntimeException e) {
            model.addAttribute("errors", e.getMessage());
            model.addAttribute("loginDto", dto);
            return "auth/login";
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
