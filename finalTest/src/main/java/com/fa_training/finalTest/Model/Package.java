package com.fa_training.finalTest.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Package")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Package {
    @Id
    @Column(length = 10)
    private String packageID;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    private Double costOfLessons;

}