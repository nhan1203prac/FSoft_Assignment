package com.practice.spring_mvc.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UserRequestDto {
    private String username;
    private String password;
    private String email;

    private String fullName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String address;

    private Set<String> roleNames;
}