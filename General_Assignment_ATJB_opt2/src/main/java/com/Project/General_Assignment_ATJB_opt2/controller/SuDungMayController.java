package com.Project.General_Assignment_ATJB_opt2.controller;

import com.Project.General_Assignment_ATJB_opt2.model.SuDungMay;
import com.Project.General_Assignment_ATJB_opt2.model.SuDungMayId;
import com.Project.General_Assignment_ATJB_opt2.service.KhachHangService;
import com.Project.General_Assignment_ATJB_opt2.service.MayService;
import com.Project.General_Assignment_ATJB_opt2.service.SuDungMayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/computer-usage")
@RequiredArgsConstructor
public class SuDungMayController {

    private final SuDungMayService suDungMayService;
    private final KhachHangService khachHangService;
    private final MayService mayService;



    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        SuDungMay usage = new SuDungMay();
        usage.setId(new SuDungMayId());

        model.addAttribute("customers", khachHangService.findAllByKeyword("", Pageable.unpaged()).getContent());
        model.addAttribute("computers", mayService.findAllByKeyword("", Pageable.unpaged()).getContent());

        if (!model.containsAttribute("computerUsage")) {
            model.addAttribute("computerUsage", usage);
        }
        return "SuDungMay/Form";
    }

    @PostMapping("/save")
    public String handleRegister(@ModelAttribute("computerUsage") SuDungMay usage, RedirectAttributes ra) {

        String result = suDungMayService.registerMachineUsage(usage);

        if ("Success".equals(result)) {
            ra.addFlashAttribute("successMsg", "Đăng ký sử dụng máy tính thành công!");
            return "redirect:/computer-usage/register";
        } else {
            ra.addFlashAttribute("errorMsg", result);
            ra.addFlashAttribute("computerUsage", usage);
            return "redirect:/computer-usage/register";
        }
    }
}