package com.Project.General_Assignment_ATJB_opt2.controller;

import com.Project.General_Assignment_ATJB_opt2.model.KhachHang;
import com.Project.General_Assignment_ATJB_opt2.service.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class KhachHangController {
    private final KhachHangService khachHangService;

    @GetMapping
    public String khachHang(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
                            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                            Model model) {

        int size = 10;
        Page<KhachHang> customerPage = khachHangService.findAllByKeyword(keyword, PageRequest.of(page, size));
        model.addAttribute("customers", customerPage.getContent());
        model.addAttribute("totalElements", customerPage.getTotalElements());
        model.addAttribute("totalPages", customerPage.getTotalPages());
        model.addAttribute("currentPage", customerPage.getNumber());
        model.addAttribute("keyword", keyword);
        return "KhachHang/List";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("mode", "create");
        model.addAttribute("customer", new KhachHang());
        return "KhachHang/Form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        KhachHang customer = khachHangService.findById(id);

        if (customer == null) {
            return "redirect:/customers";
        }
        model.addAttribute("mode", "edit");
        model.addAttribute("customer", customer);
        return "KhachHang/Form";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        khachHangService.delete(id);
        redirectAttributes.addFlashAttribute("successMsg", "Success");
        return "redirect:/customers";
    }

    @PostMapping("/save")
    public String handleSave(@ModelAttribute("customer") KhachHang kh, RedirectAttributes ra) {

        String result = khachHangService.saveOrUpdate(kh);

        if (result.equals("Success")) {
            ra.addFlashAttribute("successMsg", "Success!");
            return "redirect:/customers";
        } else {
            ra.addFlashAttribute("errorMsg", result);
            ra.addFlashAttribute("customer", kh);
            return "redirect:/customers/create";
        }
    }


}
