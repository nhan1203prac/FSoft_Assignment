package com.Project.General_Assignment_ATJB_opt2.service;

import com.Project.General_Assignment_ATJB_opt2.model.KhachHang;
import com.Project.General_Assignment_ATJB_opt2.model.May;
import com.Project.General_Assignment_ATJB_opt2.model.SuDungMay;
import com.Project.General_Assignment_ATJB_opt2.model.SuDungMayId;
import com.Project.General_Assignment_ATJB_opt2.repository.KhachHangRepository;
import com.Project.General_Assignment_ATJB_opt2.repository.MayRepository;
import com.Project.General_Assignment_ATJB_opt2.repository.SuDungMayRepository;
import com.Project.General_Assignment_ATJB_opt2.utils.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuDungMayService {
    private final SuDungMayRepository suDungMayRepository;
    private final KhachHangRepository khachHangRepository;
    private final MayRepository mayRepository;
    public String registerMachineUsage(SuDungMay sdm) {
        String maKH = (sdm.getKhachHang() != null) ? sdm.getKhachHang().getMaKH() : "";
        String maMay = (sdm.getMay() != null) ? sdm.getMay().getMaMay() : "";
        System.out.println("Log " + maKH + maMay);
        if (!Validation.validateKhachHangId(maKH)) {
            return "Mã khách hàng không hợp lệ! Phải đúng định dạng KHxxxxx (Ví dụ: KH00001).";
        }

        if (!Validation.validateMayId(maMay)) {
            return "Mã máy không hợp lệ! Phải đúng định dạng MAYxxx (Ví dụ: MAY001).";
        }

        KhachHang kh = khachHangRepository.findById(maKH).orElse(null);
        May may = mayRepository.findById(maMay).orElse(null);

        if (kh == null) return "Khách hàng không tồn tại!";
        if (may == null) return "Máy tính không tồn tại!";

        try {
            if(sdm.getId() == null){
                sdm.setId(new SuDungMayId());
            }
            sdm.setKhachHang(kh);
            sdm.setMay(may);
            sdm.getId().setKhachHang(maKH);
            sdm.getId().setMay(maMay);

            System.out.println("=== DEBUG BEFORE SAVE ===");
            System.out.println("KhachHang entity: " + kh.getMaKH());
            System.out.println("May entity: " + may.getMaMay());
            System.out.println("ID.khachHang: " + sdm.getId().getKhachHang());
            System.out.println("ID.may: " + sdm.getId().getMay());
            System.out.println("ID.ngay: " + sdm.getId().getNgayBatDauSuDung());
            System.out.println("ID.gio: " + sdm.getId().getGioBatDauSuDung());
            System.out.println("ThoiGian: " + sdm.getThoiGianSuDung());

            suDungMayRepository.save(sdm);
            return "Success";
        } catch (Exception e) {
            return "Lỗi hệ thống khi lưu: " + e.getMessage();
        }
    }
}