package com.practice.spring_mvc.config;

import com.practice.spring_mvc.entity.Post;
import com.practice.spring_mvc.entity.Role;
import com.practice.spring_mvc.entity.User;
import com.practice.spring_mvc.entity.UserProfile;
import com.practice.spring_mvc.repository.RoleRepository;
import com.practice.spring_mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(roleRepository.count() == 0){
            System.out.println("=== KHỞI TẠO DANH MỤC QUYỀN (MASTER DATA) BẰNG JAVA ===");

            Role adminRole = new Role();
            adminRole.setId("ROLE_001");
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setId("ROLE_002");
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);

            System.out.println("=== KHỞI TẠO TÀI KHOẢN MẪU (TRANSACTIONAL DATA) ===");

            User user = new User();
            user.setId("USER_001");
            user.setUsername("vinhnguyen");
            user.setPassword("vinh2026");
            user.setEmail("vinhnguyen@gmail.com");

            UserProfile profile = new UserProfile();
            profile.setFullName("Huỳnh Nguyên Vĩnh Nguyên");
            profile.setBirthDate(LocalDate.of(2004, 10, 20));
            profile.setPhoneNumber("0987654321");
            profile.setAddress("Quảng Nam");

            user.setProfile(profile);

            user.addRole(userRole);
            user.addRole(adminRole);


            Post post = new Post();
            post.setId("POST_001");
            post.setTitle("Hướng dẫn thiết kế hệ thống Spring Boot bài bản");
            post.setContent("Bài viết chia sẻ phương pháp khởi tạo dữ liệu bằng code Java DataInitializer...");

            user.addPost(post);

            userRepository.save(user);
            System.out.println("=== KHỞI TẠO DỮ LIỆU MẪU THÀNH CÔNG ===");
        }
    }
}
