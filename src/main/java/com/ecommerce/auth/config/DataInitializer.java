package com.ecommerce.auth.config;

import com.ecommerce.auth.entity.StaffProfile;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${DEFAULT_ADMIN_USERNAME}")
    private String adminUsernameConfig;

    @Value("${DEFAULT_ADMIN_PASSWORD}")
    private String adminPasswordConfig;


    @Override
    public void run(String... args) {
        String adminUsername = adminUsernameConfig;
        String adminPassword = adminPasswordConfig;

        if (adminUsername == null || adminPassword == null) {
            System.out.println("⚠️ Admin credentials not set in environment. Skipping admin creation.");
            return;
        }

        if (!userRepository.findByUsername(adminUsername).isPresent()) {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole("ADMIN");

            StaffProfile profile = new StaffProfile();
            profile.setFullName("System Admin");
            profile.setDepartment("Operations");
            profile.setEmployeeId("ADM001");
            profile.setUser(admin);

            admin.setStaffProfile(profile);

            userRepository.save(admin);
            System.out.println("✅ Admin user seeded: " + adminUsername);
        } else {
            System.out.println("ℹ️ Admin user already exists: " + adminUsername);
        }
    }
}
