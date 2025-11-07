package com.ecommerce.auth.dto;

import lombok.Data;

@Data
public class StaffRegistrationRequest {
    private String username;
    private String password;
    private String fullName;
    private String department;
    private String employeeId;
}
