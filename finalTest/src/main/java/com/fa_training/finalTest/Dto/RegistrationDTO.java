package com.fa_training.finalTest.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {

    private String registrationID;

    @NotBlank(message = "Full name is required.")
    private String fullName;

    private String phoneNumber;

    @NotNull(message = "Please display number of lessons (must be greater than 0).")
    @Min(value = 1, message = "Please display number of lessons (must be greater than 0).")
    private Integer numLessons;

    @NotNull(message = "Effective date is required.")
    private LocalDate effectiveDate;

    @NotNull(message = "Expiration date is required.")
    private LocalDate expirationDate;

    private Double totalAmount;

    @NotBlank(message = "Please select a country.")
    private String countryID;

    @NotBlank(message = "Please select a package.")
    private String packageID;

    private LocalDate registrationDate;
}
