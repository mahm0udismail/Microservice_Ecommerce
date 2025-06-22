package com.micro.productservice.product.dto;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.In;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductPurchaseRequest {
    
    @NotNull(message = "Product ID cannot be null")
    @NotBlank(message = "Product ID cannot be blank")
    Integer productId;

    @NotNull(message = "Quantity cannot be null")
    @NotBlank(message = "Quantity cannot be blank")
    Integer availableQuantity;

}
