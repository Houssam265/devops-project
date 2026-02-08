package com.ecommerce.product_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockUpdateRequest {
    @NotNull(message = "Stock quantity is required")
    private Integer stockQuantity;
}
