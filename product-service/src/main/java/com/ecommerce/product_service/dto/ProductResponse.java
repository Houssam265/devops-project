package com.ecommerce.product_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private CategoryResponse category;
    private List<ProductImageResponse> images;
    private String sku;
    private Boolean isActive;
    private Map<String, String> specifications;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
