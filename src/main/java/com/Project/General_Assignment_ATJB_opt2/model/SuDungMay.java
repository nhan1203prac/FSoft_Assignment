package com.Project.General_Assignment_ATJB_opt2.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "SUDUNGMAY")
public class SuDungMay {
    @EmbeddedId
    private SuDungMayId id;

    private int thoiGianSuDung;
}
