package com.Project.General_Assignment_ATJB_opt2.controller;

import com.Project.General_Assignment_ATJB_opt2.model.May;
import com.Project.General_Assignment_ATJB_opt2.service.MayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/computers")
@RequiredArgsConstructor
public class MayController {
    private final MayService mayService;

    @GetMapping
    public String listComputers(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
                                @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                Model model) {
        int size = 10;
        Page<May> computerPage = mayService.findAllByKeyword(keyword, PageRequest.of(page, size));

        model.addAttribute("computers", computerPage.getContent());
        model.addAttribute("totalElements", computerPage.getTotalElements());
        model.addAttribute("totalPages", computerPage.getTotalPages());
        model.addAttribute("currentPage", computerPage.getNumber());
        model.addAttribute("keyword", keyword);
        return "MayTinh/List";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("mode", "create");
        model.addAttribute("computer", new May());
        return "MayTinh/Form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        May computer = mayService.findById(id);

        if (computer == null) {
            return "redirect:/computers";
        }
        model.addAttribute("mode", "edit");
        model.addAttribute("computer", computer);
        return "MayTinh/Form";
    }

    @PostMapping("/save")
    public String handleSave(@ModelAttribute("computer") May may, RedirectAttributes ra) {
        String result = mayService.saveOrUpdate(may);

        if (result.equals("Success")) {
            ra.addFlashAttribute("successMsg", "Success!");
            return "redirect:/computers";
        } else {
            ra.addFlashAttribute("errorMsg", result);
            ra.addFlashAttribute("computer", may);
            return "redirect:/computers/create";
        }
    }

}