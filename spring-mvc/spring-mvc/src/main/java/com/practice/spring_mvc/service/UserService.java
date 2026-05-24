package com.practice.spring_mvc.service;

import com.practice.spring_mvc.dto.LoginRequestDto;
import com.practice.spring_mvc.dto.UserProfileUpdateDto;
import com.practice.spring_mvc.dto.UserRequestDto;
import com.practice.spring_mvc.dto.UserResponseDto;
import com.practice.spring_mvc.entity.Role;
import com.practice.spring_mvc.entity.User;
import com.practice.spring_mvc.entity.UserProfile;
import com.practice.spring_mvc.exception.ValidationException;
import com.practice.spring_mvc.repository.RoleRepository;
import com.practice.spring_mvc.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private synchronized String generateCustomId() {
        long count = userRepository.count();
        return String.format("USER_%03d", count + 1);
    }

    private UserResponseDto convertToResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setCreatedBy(user.getCreatedBy());

        if (user.getProfile() != null) {
            dto.setFullName(user.getProfile().getFullName());
            dto.setPhoneNumber(user.getProfile().getPhoneNumber());
            dto.setAddress(user.getProfile().getAddress());
        }

        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        dto.setRoles(roleNames);

        return dto;
    }

    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {
        Map<String, String> errors = new HashMap<>();

        // validate
        if (dto.getUsername() == null || dto.getUsername().trim().isEmpty()) {
            errors.put("username", "Tên tài khoản bắt buộc phải nhập!");
        }else{
            String username = dto.getUsername().trim();
            if (username.length() < 8) {
                errors.put("username", "Tên tài khoản nghiệp vụ phải chứa ít nhất từ 8 ký tự trở lên!");
            } else if (username.contains(" ")) {
                errors.put("username", "Tên tài khoản không được phép chứa khoảng trắng!");
            } else if (userRepository.existsByUsername(username)) {
                errors.put("username", "Tên tài khoản này đã tồn tại trên hệ thống RAM!");
            }
        }

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            errors.put("email", "Địa chỉ Email bắt buộc phải điền!");
        } else {
            String email = dto.getEmail().trim();
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            if (!email.matches(emailRegex)) {
                errors.put("email", "Định dạng Email không hợp lệ");
            } else if (userRepository.existsByEmail(email)) {
                errors.put("email", "Địa chỉ Email này đã được đăng ký bởi một tài khoản khác!");
            }
        }

        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            errors.put("password", "Mật khẩu không được phép bỏ trống!");
        } else if (dto.getPassword().length() < 6) {
            errors.put("password", "Mật khẩu an toàn phải có độ dài từ 6 ký tự trở lên!");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        User user = new User();
        user.setId(generateCustomId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Khởi tạo và liên kết thông tin Hồ sơ phụ (Quan hệ 1-1)
        UserProfile profile = new UserProfile();
        profile.setFullName(dto.getFullName());
        profile.setBirthDate(dto.getBirthDate());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setAddress(dto.getAddress());

        // Sử dụng Helper Method để đồng bộ hai chiều liên kết chặt chẽ
        user.setProfile(profile);

        // Ánh xạ liên kết quyền hạn (Quan hệ N-N)
        if (dto.getRoleNames() != null) {
            for (String roleName : dto.getRoleNames()) {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền hệ thống: " + roleName));
                user.addRole(role);
            }
        }else {
            Role defaultRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Lỗi hệ thống: Vai trò mặc định ROLE_USER chưa được khởi tạo dưới RAM!"));
            user.addRole(defaultRole);
        }

        User savedUser = userRepository.save(user);
        return convertToResponseDto(savedUser);
    }

    @Transactional
    public UserResponseDto userUpdateOwnProfile(String id, UserProfileUpdateDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản người dùng!"));

        Map<String, String> errors = new HashMap<>();

        if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
            if (dto.getPassword().length() < 6) {
                errors.put("password", "Mật khẩu đổi mới phải chứa từ 6 ký tự trở lên!");
            } else {
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        UserProfile profile = user.getProfile();
        if (profile != null) {
            profile.setFullName(dto.getFullName());
            profile.setBirthDate(dto.getBirthDate());
            profile.setPhoneNumber(dto.getPhoneNumber());
            profile.setAddress(dto.getAddress());
        }

        User savedUser = userRepository.save(user);
        return convertToResponseDto(savedUser);
    }

    public UserResponseDto login(LoginRequestDto dto, jakarta.servlet.http.HttpServletRequest request) {
        if (dto.getUsername() == null || dto.getUsername().trim().isEmpty() ||
                dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Tên đăng nhập và mật khẩu không được để trống!");
        }
        User user = userRepository.findByUsername(dto.getUsername().trim())
                .orElseThrow(() -> new RuntimeException("Tên đăng nhập không chính xác hoặc tài khoản không tồn tại!"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mật khẩu xác thực không chính xác!");
        }

        HttpSession session = request.getSession();

        UserResponseDto userResponse = convertToResponseDto(user);
        session.setAttribute("currentUser", userResponse);

        return userResponse;
    }


    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Người dùng có mã số " + id + " không tồn tại!"));
        return convertToResponseDto(user);
    }
}
