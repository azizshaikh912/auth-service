package com.ecommerce.auth.dto;

import lombok.Data;

@Data
public class CustomerRegistrationRequest {
    private String username;
    private String password;
    private String fullName;
    private String address;
    private String phone;
}
