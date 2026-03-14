package com.ecommerce.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderLineRequest(
        Integer id,

        @NotNull(message = "Product ID is required")
        Integer productId,

        @Positive(message = "Quantity must be positive")
        double quantity
) {}
