package com.ecommerce.user_service.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    
    @Email(message = "Email must be valid")
    private String email;
    
    private String phone;
}
