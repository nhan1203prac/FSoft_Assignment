package com.Project.General_Assignment_ATJB_opt2.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class SuDungMayId implements Serializable {
    private String maKH;
    private String maMay;
    private LocalDate ngayBatDauSuDung;
    private String gioBatDauSuDung;

}
