package com.ecommerce.product_service.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.TextIndexed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    
    @Indexed
    private String name;
    
    @TextIndexed
    private String description;
    
    private Double price;
    private Integer stockQuantity;
    
    @DBRef
    private Category category;
    
    private String categoryId;
    
    private List<ProductImage> images = new ArrayList<>();
    
    @Indexed(unique = true)
    private String sku;
    
    private Boolean isActive = true;
    
    private Map<String, String> specifications = new HashMap<>();
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
