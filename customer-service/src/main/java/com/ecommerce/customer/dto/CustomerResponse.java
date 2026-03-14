package com.ecommerce.customer.dto;

import com.ecommerce.customer.model.Address;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email,
        Address address
) {}
