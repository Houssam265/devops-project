package com.ecommerce.product_service.dto;

import lombok.Data;

@Data
public class ProductImageResponse {
    private String url;
    private Boolean isPrimary;
}
