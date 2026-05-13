package com.Project.General_Assignment_ATJB_opt2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MAY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class May {
    @Id
    private String maMay;
    private String viTri;
    private String trangThai;
}