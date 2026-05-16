package com.Project.General_Assignment_ATJB_opt2.service;

import com.Project.General_Assignment_ATJB_opt2.model.DichVu;
import com.Project.General_Assignment_ATJB_opt2.repository.DichVuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DichVuService {
    private final DichVuRepository dichVuRepository;

    public Page<DichVu> findAllByKeyword(String keyword, Pageable pageable) {
        return dichVuRepository.findAllByKeyword(keyword, pageable);
    }

    public DichVu findById(String id) {
        return dichVuRepository.findById(id).orElse(null);
    }

    public void delete(String id) {
        dichVuRepository.deleteById(id);
    }

    public String saveOrUpdate(DichVu dv) {
        try {
            dichVuRepository.save(dv);
            return "Success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}