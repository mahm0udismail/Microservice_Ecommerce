package com.micro.productservice.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
    @NotNull(message = "Product ID cannot be null")
    Integer productId,

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    double quantity
) {}