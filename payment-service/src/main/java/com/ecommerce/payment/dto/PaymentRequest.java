package com.ecommerce.payment.dto;

import com.ecommerce.payment.model.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,

        @Positive(message = "Amount must be positive")
        BigDecimal amount,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "Order ID is required")
        Integer orderId,

        String orderReference,

        CustomerDto customer
) {}
