package com.ecommerce.notification.dto;

import java.math.BigDecimal;

public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal amount,
        String paymentMethod,
        String customerEmail,
        String customerFirstname,
        String customerLastname
) {}
