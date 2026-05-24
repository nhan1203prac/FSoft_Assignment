package com.Project.General_Assignment_ATJB_opt2.repository;

import com.Project.General_Assignment_ATJB_opt2.dto.UsageSummaryDTO;
import com.Project.General_Assignment_ATJB_opt2.model.SuDungMay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SummaryRepository extends JpaRepository<SuDungMay, Object> {

    @Query("SELECT new com.Project.General_Assignment_ATJB_opt2.dto.UsageSummaryDTO(" +
            "kh.maKH, kh.tenKH, m.maMay, m.viTri, m.trangThai, " +
            "sdm.id.ngayBatDauSuDung, sdm.id.gioBatDauSuDung, sdm.thoiGianSuDung, " +
            "dv.maDV, sddv.id.ngaySuDung, sddv.id.gioSuDung, sddv.soLuong, " +
            "(SELECT SUM(sdv2.soLuong * d2.donGia) FROM SuDungDichVu sdv2 JOIN sdv2.dichVu d2 ON sdv2.dichVu.maDV = d2.maDV WHERE sdv2.khachHang.maKH = kh.maKH)) " +
            "FROM SuDungMay sdm " +
            "JOIN sdm.khachHang kh ON sdm.khachHang.maKH = kh.maKH " +
            "JOIN sdm.may m ON sdm.may.maMay = m.maMay " +
            "LEFT JOIN SuDungDichVu sddv ON sddv.khachHang.maKH = kh.maKH " +
            "LEFT JOIN DichVu dv ON sddv.dichVu.maDV = dv.maDV")
    Page<UsageSummaryDTO> findAllReport(Pageable pageable);
}