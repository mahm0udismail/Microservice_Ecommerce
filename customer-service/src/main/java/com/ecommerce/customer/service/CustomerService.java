package com.ecommerce.customer.service;

import com.ecommerce.customer.dto.CustomerRequest;
import com.ecommerce.customer.dto.CustomerResponse;
import com.ecommerce.customer.model.Customer;
import com.ecommerce.customer.repository.CustomerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(@NonNull CustomerRequest request) {
        @NonNull Customer customer = mapper.toCustomer(request);
        return repository.save(customer).getId();
    }

    public void updateCustomer(@NonNull CustomerRequest request) {
        @NonNull Customer customer = repository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Cannot update customer: no customer found with id " + request.id()
                ));
        mergeCustomer(customer, request);
        repository.save(customer);
    }

    private void mergeCustomer(@NonNull Customer customer, @NonNull CustomerRequest request) {
        if (hasText(request.firstname())) {
            customer.setFirstname(request.firstname());
        }
        if (hasText(request.lastname())) {
            customer.setLastname(request.lastname());
        }
        if (hasText(request.email())) {
            customer.setEmail(request.email());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    /** Replaces StringUtils.isNotBlank — no commons-lang3 needed */
    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public List<CustomerResponse> findAllCustomers() {
        return repository.findAll()
                .stream()
                .map(mapper::toCustomerResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse findById(@NonNull String customerId) {
        return repository.findById(customerId)
                .map(mapper::toCustomerResponse)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "No customer found with id: " + customerId
                ));
    }

    public boolean existsById(@NonNull String customerId) {
        return repository.existsById(customerId);
    }

    public void deleteCustomer(@NonNull String customerId) {
        if (!repository.existsById(customerId)) {
            throw new CustomerNotFoundException("No customer found with id: " + customerId);
        }
        repository.deleteById(customerId);
    }
}