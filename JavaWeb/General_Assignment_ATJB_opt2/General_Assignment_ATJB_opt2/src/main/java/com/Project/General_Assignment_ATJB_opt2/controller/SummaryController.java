package com.Project.General_Assignment_ATJB_opt2.controller;

import com.Project.General_Assignment_ATJB_opt2.dto.UsageSummaryDTO;
import com.Project.General_Assignment_ATJB_opt2.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @GetMapping("/usage")
    public String showUsageReport(Model model,
                                  @RequestParam(defaultValue = "0") int page) {

        int pageSize = 5;
        Page<UsageSummaryDTO> reportPage = summaryService.getUsageReport(page, pageSize);

        model.addAttribute("reports", reportPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reportPage.getTotalPages());

        return "Summary/List";
    }
}