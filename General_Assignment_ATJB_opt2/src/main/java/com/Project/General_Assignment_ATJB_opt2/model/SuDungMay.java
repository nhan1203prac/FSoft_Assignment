package com.Project.General_Assignment_ATJB_opt2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SUDUNGMAY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuDungMay {
    @EmbeddedId
    private SuDungMayId id;

    @ManyToOne
    @MapsId("khachHang")
//    @JoinColumn(name = "maKH")
    private KhachHang khachHang;

    @ManyToOne
    @MapsId("may")
//    @JoinColumn(name = "maMay")
    private May may;

    private int thoiGianSuDung;
}
