package com.ecommerce.customer.dto;

import com.ecommerce.customer.model.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
        String id,

        @NotBlank(message = "First name is required")
        String firstname,

        @NotBlank(message = "Last name is required")
        String lastname,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        Address address
) {}
