package com.ecommerce.auth.service;

import com.ecommerce.auth.entity.CustomerProfile;
import com.ecommerce.auth.entity.StaffProfile;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.repository.CustomerProfileRepository;
import com.ecommerce.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final CustomerProfileRepository customerRepo;
    private final PasswordEncoder passwordEncoder;

    public User registerCustomer(String username, String rawPassword, String fullName, String address, String phone) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole("CUSTOMER");

        CustomerProfile profile = new CustomerProfile();
        profile.setFullName(fullName);
        profile.setAddress(address);
        profile.setPhone(phone);
        profile.setLoyaltyTier("BRONZE");
        profile.setUser(user);

        user.setCustomerProfile(profile);

        return userRepository.save(user); // cascades profile save
    }
    public User registerStaff(String username, String rawPassword, String fullName, String department, String employeeId) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole("STAFF");

        StaffProfile profile = new StaffProfile();
        profile.setFullName(fullName);
        profile.setDepartment(department);
        profile.setEmployeeId(employeeId);
        profile.setUser(user);

        user.setStaffProfile(profile);

        return userRepository.save(user);
    }

    public User registerAdmin(String username, String rawPassword, String fullName, String department, String employeeId) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole("ADMIN");

        StaffProfile profile = new StaffProfile();
        profile.setFullName(fullName);
        profile.setDepartment(department);
        profile.setEmployeeId(employeeId);
        profile.setUser(user);

        user.setStaffProfile(profile);

        return userRepository.save(user);
    }

}
