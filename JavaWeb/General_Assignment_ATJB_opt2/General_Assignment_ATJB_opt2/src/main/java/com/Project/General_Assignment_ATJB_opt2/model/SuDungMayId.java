package com.Project.General_Assignment_ATJB_opt2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
public class SuDungMayId implements Serializable {
//    @Column(name = "maKH")
    private String khachHang;
//    @Column(name = "maMay")
    private String may;
    private LocalDate ngayBatDauSuDung;
    private String gioBatDauSuDung;

}
