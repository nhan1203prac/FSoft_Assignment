package com.practice.spring_mvc.config;

import com.practice.spring_mvc.dto.UserResponseDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

public class AuditConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(false);
        if (session != null) {
            UserResponseDto currentUser = (UserResponseDto) session.getAttribute("currentUser");

            if (currentUser != null) {
                return Optional.of(currentUser.getUsername());
            }
        }
        return Optional.empty();
    }
}
