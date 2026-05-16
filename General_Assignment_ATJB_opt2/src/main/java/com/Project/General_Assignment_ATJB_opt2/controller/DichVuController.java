package com.Project.General_Assignment_ATJB_opt2.controller;

import com.Project.General_Assignment_ATJB_opt2.model.DichVu;
import com.Project.General_Assignment_ATJB_opt2.service.DichVuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/services")
@RequiredArgsConstructor
public class DichVuController {
    private final DichVuService dichVuService;

    @GetMapping
    public String listServices(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
                               @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                               Model model) {
        int size = 10;
        Page<DichVu> servicePage = dichVuService.findAllByKeyword(keyword, PageRequest.of(page, size));

        model.addAttribute("services", servicePage.getContent());
        model.addAttribute("totalElements", servicePage.getTotalElements());
        model.addAttribute("totalPages", servicePage.getTotalPages());
        model.addAttribute("currentPage", servicePage.getNumber());
        model.addAttribute("keyword", keyword);
        return "DichVu/List";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("mode", "create");
        model.addAttribute("dichVu", new DichVu());
        return "DichVu/Form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        DichVu dichVu = dichVuService.findById(id);

        if (dichVu == null) {
            return "redirect:/services";
        }
        model.addAttribute("mode", "edit");
        model.addAttribute("dichVu", dichVu);
        return "DichVu/Form";
    }

    @PostMapping("/save")
    public String handleSave(@ModelAttribute("dichVu") DichVu dv, RedirectAttributes ra) {
        String result = dichVuService.saveOrUpdate(dv);
        if (result.equals("Success")) {
            ra.addFlashAttribute("successMsg", "Success!");
            return "redirect:/services";
        } else {
            ra.addFlashAttribute("errorMsg", result);
            return "redirect:/services/create";
        }
    }
}