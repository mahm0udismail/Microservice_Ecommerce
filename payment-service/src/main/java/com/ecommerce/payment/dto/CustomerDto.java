package com.ecommerce.payment.dto;

public record CustomerDto(
        String id,
        String firstname,
        String lastname,
        String email
) {}
