package com.ecommerce.auth.controller;

import com.ecommerce.auth.dto.CustomerRegistrationRequest;
import com.ecommerce.auth.dto.LoginRequest;
import com.ecommerce.auth.dto.StaffRegistrationRequest;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.repository.UserRepository;
import com.ecommerce.auth.service.AuthService;
import com.ecommerce.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register/customer")
    public ResponseEntity<User> registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        User user = authService.registerCustomer(
                request.getUsername(),
                request.getPassword(),
                request.getFullName(),
                request.getAddress(),
                request.getPhone()
        );
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register/staff")
    public ResponseEntity<User> registerStaff(@RequestBody StaffRegistrationRequest request) {
        User user = authService.registerStaff(
                request.getUsername(),
                request.getPassword(),
                request.getFullName(),
                request.getDepartment(),
                request.getEmployeeId()
        );
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/admin")
    public ResponseEntity<User> registerAdmin(@RequestBody StaffRegistrationRequest request) {
        User user = authService.registerAdmin(
                request.getUsername(),
                request.getPassword(),
                request.getFullName(),
                request.getDepartment(),
                request.getEmployeeId()
        );
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}
