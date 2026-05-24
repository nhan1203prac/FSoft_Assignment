package com.Project.General_Assignment_ATJB_opt2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SUDUNGDICHVU")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuDungDichVu {
    @EmbeddedId
    private SuDungDichVuId id;

    @ManyToOne
    @MapsId("khachHang")
    @JoinColumn(name = "maKH")
    private KhachHang khachHang;

    @ManyToOne
    @MapsId("dichVu")
    @JoinColumn(name = "maDV")
    private DichVu dichVu;

    private int soLuong;
}