package com.ecommerce.product_service.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "categories")
public class Category {
    @Id
    private String id;
    
    @Indexed
    private String name;
    private String description;
    private String parentId;
    
    @CreatedDate
    private LocalDateTime createdAt;
}
