package com.ecommerce.user_service.dto;

import com.ecommerce.user_service.model.Address;
import com.ecommerce.user_service.model.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private List<Address> addresses;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
