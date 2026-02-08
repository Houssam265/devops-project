package com.ecommerce.product_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryResponse {
    private String id;
    private String name;
    private String description;
    private String parentId;
    private LocalDateTime createdAt;
}
