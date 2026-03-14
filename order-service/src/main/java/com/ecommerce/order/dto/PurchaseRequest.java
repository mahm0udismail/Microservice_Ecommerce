package com.ecommerce.order.dto;

public record PurchaseRequest(
        Integer productId,
        double quantity
) {}
