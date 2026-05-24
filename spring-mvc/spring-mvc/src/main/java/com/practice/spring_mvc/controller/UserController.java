package com.practice.spring_mvc.controller;

import com.practice.spring_mvc.dto.UserProfileUpdateDto;
import com.practice.spring_mvc.dto.UserResponseDto;
import com.practice.spring_mvc.exception.ValidationException;
import com.practice.spring_mvc.repository.RoleRepository;
import com.practice.spring_mvc.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    // ─── 1. HIỂN THỊ DANH SÁCH USER (CÓ KIỂM TRA ĐĂNG NHẬP) ───
    @GetMapping
    public String listUsers(Model model, HttpSession session) {
        // Chặn an toàn bề nổi: Chưa đăng nhập (Session trống) thì đá ra ngoài ngay
        UserResponseDto currentUser = (UserResponseDto) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("currentUser", currentUser); // Đẩy ra để hiển thị tên người đang dùng trên Menu
        return "user/list"; // src/main/resources/templates/user/list.html
    }

    @GetMapping("/my-profile/edit")
    public String showMyProfileForm(Model model, HttpSession session) {
        UserResponseDto currentUser = (UserResponseDto) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        UserResponseDto userDto = userService.getUserById(currentUser.getId());

        UserProfileUpdateDto profileDto = new UserProfileUpdateDto();
        profileDto.setFullName(userDto.getFullName());
        profileDto.setPhoneNumber(userDto.getPhoneNumber());
        profileDto.setAddress(userDto.getAddress());

        model.addAttribute("profileDto", profileDto);
        model.addAttribute("userData", userDto); // Đẩy thêm data phụ để hiện Email/Username ở dạng Read-only
        return "user/edit-profile"; // src/main/resources/templates/user/edit-profile.html
    }

    @PostMapping("/my-profile/edit")
    public String handleUserUpdateOwnProfile(@ModelAttribute("profileDto") UserProfileUpdateDto dto,
                                             HttpSession session,
                                             Model model) {
        UserResponseDto currentUser = (UserResponseDto) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            userService.userUpdateOwnProfile(currentUser.getId(), dto);

            UserResponseDto updatedUser = userService.getUserById(currentUser.getId());
            session.setAttribute("currentUser", updatedUser);

            return "redirect:/users?updateSuccess=true";
        } catch (ValidationException e) {
            model.addAttribute("errors", e.getErrors());
            model.addAttribute("userData", currentUser);
            return "user/edit-profile";
        } catch (RuntimeException e) {
            model.addAttribute("globalError", e.getMessage());
            model.addAttribute("userData", currentUser);
            return "user/edit-profile";
        }
    }


}