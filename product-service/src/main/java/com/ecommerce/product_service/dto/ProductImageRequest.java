package com.ecommerce.product_service.dto;

import lombok.Data;

@Data
public class ProductImageRequest {
    private String url;
    private Boolean isPrimary = false;
}
