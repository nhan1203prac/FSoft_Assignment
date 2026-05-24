package com.Project.General_Assignment_ATJB_opt2.service;

import com.Project.General_Assignment_ATJB_opt2.model.May;
import com.Project.General_Assignment_ATJB_opt2.repository.MayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MayService {
    private final MayRepository mayRepository;

    public Page<May> findAllByKeyword(String keyword, Pageable pageable) {
        return mayRepository.findAllByKeyword(keyword, pageable);
    }

    public May findById(String id) {
        return mayRepository.findById(id).orElse(null);
    }

    public String saveOrUpdate(May may) {
        try {
            mayRepository.save(may);
            return "Success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}