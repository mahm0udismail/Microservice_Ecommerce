package com.ecommerce.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        Integer id,

        @NotBlank(message = "Product name is required")
        String name,

        String description,

        @Positive(message = "Available quantity must be positive")
        double availableQuantity,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @NotNull(message = "Category ID is required")
        Integer categoryId
) {}
