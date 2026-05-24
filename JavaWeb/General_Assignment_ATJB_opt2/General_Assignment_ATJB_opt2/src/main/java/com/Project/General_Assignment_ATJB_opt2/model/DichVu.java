package com.Project.General_Assignment_ATJB_opt2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DICHVU")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DichVu {
    @Id
    private String maDV;
    private String tenDV;
    private String donViTinh;
    private double donGia;
}
