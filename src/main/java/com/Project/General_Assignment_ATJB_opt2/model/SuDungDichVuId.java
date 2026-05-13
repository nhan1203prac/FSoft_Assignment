package com.Project.General_Assignment_ATJB_opt2.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class SuDungDichVuId implements Serializable {
    private String maKH;
    private String maDV;
    private LocalDate ngaySuDung;
    private String gioSuDung;
}
