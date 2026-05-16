package com.Project.General_Assignment_ATJB_opt2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UsageSummaryDTO {
    private String maKH;
    private String tenKH;
    private String maMay;
    private String viTri;
    private String trangThai;
    private LocalDate ngayBatDauMay;
    private String gioBatDauMay;
    private int thoiGianSuDung;
    private String maDV;
    private LocalDate ngaySuDungDV;
    private String gioSuDungDV;
    private int soLuong;
    private long tongTienDichVu;
}