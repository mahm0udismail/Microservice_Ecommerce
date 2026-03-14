package com.ecommerce.customer.service;

import com.ecommerce.customer.dto.CustomerRequest;
import com.ecommerce.customer.dto.CustomerResponse;
import com.ecommerce.customer.model.Customer;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomerMapper {

    public @NonNull Customer toCustomer(@NonNull CustomerRequest request) {
        return Objects.requireNonNull(
                Customer.builder()
                        .id(request.id())
                        .firstname(request.firstname())
                        .lastname(request.lastname())
                        .email(request.email())
                        .address(request.address())
                        .build(),
                "Customer builder returned null"
        );
    }

    public @NonNull CustomerResponse toCustomerResponse(@NonNull Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}