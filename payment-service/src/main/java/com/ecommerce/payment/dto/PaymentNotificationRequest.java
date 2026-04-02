package com.ecommerce.payment.dto;

import com.ecommerce.payment.model.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerEmail,
        String customerFirstname,
        String customerLastname
) {}
