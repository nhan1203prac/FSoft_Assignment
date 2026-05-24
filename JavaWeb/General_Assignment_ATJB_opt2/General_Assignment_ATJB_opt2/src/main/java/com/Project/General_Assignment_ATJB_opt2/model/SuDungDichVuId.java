package com.Project.General_Assignment_ATJB_opt2.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
public class SuDungDichVuId implements Serializable {
    private String khachHang;
    private String dichVu;
    private LocalDate ngaySuDung;
    private String gioSuDung;
}
