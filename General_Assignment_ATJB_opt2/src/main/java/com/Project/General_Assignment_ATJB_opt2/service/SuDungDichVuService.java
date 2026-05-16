package com.Project.General_Assignment_ATJB_opt2.service;

import com.Project.General_Assignment_ATJB_opt2.model.SuDungDichVu;
import com.Project.General_Assignment_ATJB_opt2.repository.SuDungDichVuRepository;
import com.Project.General_Assignment_ATJB_opt2.utils.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuDungDichVuService {
    private final SuDungDichVuRepository suDungDichVuRepository;

    public String registerServiceUsage(SuDungDichVu sddv) {
        if (sddv.getKhachHang() == null || sddv.getKhachHang().getMaKH().trim().isEmpty()) {
            return "Vui lòng chọn khách hàng!";
        }
        if (sddv.getDichVu() == null || sddv.getDichVu().getMaDV().trim().isEmpty()) {
            return "Vui lòng chọn dịch vụ!";
        }

        if (sddv.getId().getNgaySuDung() == null) {
            return "Ngày sử dụng dịch vụ không được để trống!";
        }
        if(!Validation.validateDate(sddv.getId().getNgaySuDung())){
            return "Ngày sử dụng dịch vụ không hợp lệ!";
        }

        String gioSuDung = sddv.getId().getGioSuDung();
        if (gioSuDung == null || gioSuDung.trim().isEmpty()) {
            return "Giờ sử dụng dịch vụ không được để trống!";
        }
        if (!Validation.validateTime(gioSuDung)) {
            return "Giờ sử dụng không hợp lệ! Phải đúng định dạng 24h (Ví dụ: 07:15, 18:30).";
        }

        if (!Validation.isPositiveInteger(sddv.getSoLuong())) {
            return "Số lượng sử dụng dịch vụ phải là số nguyên dương lớn hơn 0!";
        }

        try {
            sddv.getId().setKhachHang(sddv.getKhachHang().getMaKH());
            sddv.getId().setDichVu(sddv.getDichVu().getMaDV());

            suDungDichVuRepository.save(sddv);
            return "Success";
        } catch (Exception e) {
            return "Lỗi hệ thống: " + e.getMessage();
        }
    }
}