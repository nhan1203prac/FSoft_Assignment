package com.Project.General_Assignment_ATJB_opt2.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    private int soLuong;
}