package com.practice.spring_mvc.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class UserResponseDto {
    private String id;
    private String username;
    private String email;

    // Thông tin từ bảng 1-1 phẳng hóa ra DTO
    private String fullName;
    private String phoneNumber;
    private String address;

    // Danh sách quyền dạng chuỗi gọn nhẹ
    private Set<String> roles;

    // Thông tin lịch sử hệ thống (Audit sài chung)
    private LocalDateTime createdAt;
    private String createdBy;
}