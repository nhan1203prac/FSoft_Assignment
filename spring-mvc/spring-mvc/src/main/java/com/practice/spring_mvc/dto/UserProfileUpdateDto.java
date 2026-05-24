package com.practice.spring_mvc.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
public class UserProfileUpdateDto {
    private String fullName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String address;
    private String password;
}