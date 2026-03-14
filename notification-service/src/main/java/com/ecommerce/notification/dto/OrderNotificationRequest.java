package com.ecommerce.notification.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderNotificationRequest(
        String orderReference,
        BigDecimal totalAmount,
        String customerEmail,
        String customerFirstname,
        String customerLastname,
        List<ProductPurchaseResponse> products
) {}
