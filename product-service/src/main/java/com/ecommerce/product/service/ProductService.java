package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.dto.PurchaseRequest;
import com.ecommerce.product.dto.PurchaseResponse;
import com.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest request) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    @Transactional
    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requests) {

        var productIds = requests.stream()
                .map(PurchaseRequest::productId)
                .toList();

        var storedProducts = repository.findAllByIdInOrderById(productIds);

        if (storedProducts.size() != productIds.size()) {
            throw new ProductPurchaseException("One or more products do not exist");
        }

        var requestMap = requests.stream()
                .collect(Collectors.toMap(
                        PurchaseRequest::productId,
                        r -> r
                ));

        var purchasedProducts = new ArrayList<PurchaseResponse>();

        for (var product : storedProducts) {

            var request = requestMap.get(product.getId());

            if (product.getAvailableQuantity() < request.quantity()) {
                throw new ProductPurchaseException(
                        "Insufficient stock for product: " + product.getName()
                                + ". Available: " + product.getAvailableQuantity()
                                + ", Requested: " + request.quantity()
                );
            }

            product.setAvailableQuantity(product.getAvailableQuantity() - request.quantity());
            repository.save(product);

            purchasedProducts.add(new PurchaseResponse(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    request.quantity()
            ));
        }

        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new ProductNotFoundException(
                        "No product found with id: " + productId
                ));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .toList();
    }
}
