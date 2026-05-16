package com.fa_training.finalTest.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Registration")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registration {
    @Id
    @Column(length = 10)
    private String registrationID;

    @Column(nullable = false, columnDefinition = "nvarchar(150)")
    private String fullName;

    @Column(length = 15)
    private String phoneNumber;

    private Integer numLessons;

    private LocalDate effectiveDate;

    private LocalDate expirationDate;

    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "countryID")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "packageID")
    private Package lessonPackage;
}
