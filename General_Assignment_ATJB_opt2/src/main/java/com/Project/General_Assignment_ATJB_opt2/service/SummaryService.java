package com.Project.General_Assignment_ATJB_opt2.service;

import com.Project.General_Assignment_ATJB_opt2.dto.UsageSummaryDTO;
import com.Project.General_Assignment_ATJB_opt2.repository.SummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final SummaryRepository summaryRepository;

    public Page<UsageSummaryDTO> getUsageReport(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return summaryRepository.findAllReport(pageable);
    }
}