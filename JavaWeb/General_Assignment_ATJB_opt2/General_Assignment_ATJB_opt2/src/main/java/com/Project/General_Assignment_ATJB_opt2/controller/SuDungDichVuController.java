package com.Project.General_Assignment_ATJB_opt2.controller;

import com.Project.General_Assignment_ATJB_opt2.model.SuDungDichVu;
import com.Project.General_Assignment_ATJB_opt2.model.SuDungDichVuId;
import com.Project.General_Assignment_ATJB_opt2.service.KhachHangService;
import com.Project.General_Assignment_ATJB_opt2.service.DichVuService;
import com.Project.General_Assignment_ATJB_opt2.service.SuDungDichVuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/service-usage")
@RequiredArgsConstructor
public class SuDungDichVuController {

    private final SuDungDichVuService suDungDichVuService;
    private final KhachHangService khachHangService;
    private final DichVuService dichVuService;


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        SuDungDichVu usage = new SuDungDichVu();
        usage.setId(new SuDungDichVuId());

        model.addAttribute("customers", khachHangService.findAllByKeyword("", Pageable.unpaged()).getContent());
        model.addAttribute("services", dichVuService.findAllByKeyword("", Pageable.unpaged()).getContent());

        if (!model.containsAttribute("serviceUsage")) {
            model.addAttribute("serviceUsage", usage);
        }

        return "SuDungDichVu/Form";
    }

    @PostMapping("/save")
    public String handleRegister(@ModelAttribute("serviceUsage") SuDungDichVu usage, RedirectAttributes ra) {

        String result = suDungDichVuService.registerServiceUsage(usage);

        if ("Success".equals(result)) {
            ra.addFlashAttribute("successMsg", "Đăng ký sử dụng dịch vụ thành công!");
            return "redirect:/service-usage/register";
        } else {
            ra.addFlashAttribute("errorMsg", result);
            ra.addFlashAttribute("serviceUsage", usage);
            return "redirect:/service-usage/register";
        }
    }
}