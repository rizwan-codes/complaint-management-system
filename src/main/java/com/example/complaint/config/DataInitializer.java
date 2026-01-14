package com.example.complaint.config;

import com.example.complaint.entity.User;
import com.example.complaint.enums.Role;
import com.example.complaint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        //Api EndPoint for test (http://localhost:8080/auth/login) with Body raw json
        if (!userRepository.existsByUsername("rizwan")) {
            User admin = new User();
            admin.setUsername("rizwan");
            admin.setEmail("rizwan@example.com");
            admin.setPassword(passwordEncoder.encode("rizwan123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Default admin created: username=rizwan, password=rizwan123");
        }

        if (!userRepository.existsByUsername("yaqoob")) {
            User user = new User();
            user.setUsername("yaqoob");
            user.setEmail("yaqoob@example.com");
            user.setPassword(passwordEncoder.encode("yaqoob123"));
            user.setRole(Role.USER);
            userRepository.save(user);
            System.out.println("Default user created: username=yaqoob, password=yaqoob123");
        }
    }
}
