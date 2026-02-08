package com.ecommerce.user_service.controller;

import com.ecommerce.user_service.dto.*;
import com.ecommerce.user_service.model.Address;
import com.ecommerce.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        UserResponse response = userService.getCurrentUser();
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateCurrentUser(@Valid @RequestBody UpdateUserRequest request) {
        UserResponse response = userService.updateCurrentUser(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/addresses")
    public ResponseEntity<Address> addAddress(@Valid @RequestBody AddressRequest request) {
        Address address = userService.addAddress(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }
    
    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> getAddresses() {
        List<Address> addresses = userService.getAddresses();
        return ResponseEntity.ok(addresses);
    }
    
    @PutMapping("/addresses/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable String id, 
                                                  @Valid @RequestBody AddressRequest request) {
        Address address = userService.updateAddress(id, request);
        return ResponseEntity.ok(address);
    }
    
    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable String id) {
        userService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
