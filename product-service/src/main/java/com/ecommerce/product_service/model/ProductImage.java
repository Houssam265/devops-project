package com.ecommerce.product_service.model;

import lombok.Data;

@Data
public class ProductImage {
    private String url;
    private Boolean isPrimary = false;
}
