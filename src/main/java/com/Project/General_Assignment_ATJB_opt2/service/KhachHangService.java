package com.Project.General_Assignment_ATJB_opt2.service;

import com.Project.General_Assignment_ATJB_opt2.model.KhachHang;
import com.Project.General_Assignment_ATJB_opt2.repository.KhachHangRepository;
import com.Project.General_Assignment_ATJB_opt2.utils.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KhachHangService {

    private final KhachHangRepository khachHangRepository;

    public Page<KhachHang> findAllByKeyword(String keyword, Pageable pageable) {
        return khachHangRepository.findAllByKeyword(keyword, pageable);
    }

    public KhachHang findById(String id) {
        return khachHangRepository.findById(id).orElse(null);
    }

    public String saveOrUpdate(KhachHang kh) {
        if (!Validation.validateKhachHangId(kh.getMaKH())) {
            return "Mã khách hàng không đúng định dạng KHxxxxx!";
        }
        if (!Validation.validatePhone(kh.getSoDienThoai())) {
            return "Số điện thoại không hợp lệ!";
        }
        if (!Validation.validateEmail(kh.getDiaChiEmail())) {
            return "Địa chỉ email không hợp lệ!";
        }

        khachHangRepository.save(kh);
        return "Success";
    }


    public void delete(String id) {
        KhachHang kh = findById(id);
        if (kh != null) {
            khachHangRepository.delete(kh);
        }
    }
}
