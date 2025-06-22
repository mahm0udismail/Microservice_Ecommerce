package com.micro.productservice.product.dto;
import java.math.BigDecimal;


public record ProductPurchaseResponse(
    Integer productId,
    String productName,
    double price,
    double quantity,
    BigDecimal totalPrice
) {
    
}
