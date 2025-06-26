package com.micro.productservice.product.service;

import com.micro.productservice.product.repository.ProductRepository;

import com.micro.productservice.product.mapper.ProductMapper;

import com.micro.productservice.product.model.Product;

import com.micro.productservice.product.dto.ProductRequest;
import com.micro.productservice.product.dto.ProductResponse;
import com.micro.productservice.product.dto.ProductPurchaseRequest;
import com.micro.productservice.product.dto.ProductPurchaseResponse;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import com.micro.productservice.product.dto.ProductPurchaseResponse;
import com.micro.productservice.product.exception.ProductNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest request) {
        Product product = mapper.toProduct(request);
        Product savedProduct = repository.save(product);
        return savedProduct.getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productsIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = repository.findAllByIdInOrderById(productsIds);

        if (storedProducts.isEmpty()) {
            throw new ProductPurchaseException("no products to purchase");
        }

        if (storedProducts.size() != productsIds.size()) {
            throw new ProductPurchaseException("one or more products does not exist");
        }
        
        var storedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for (int i=0; i<storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("product with id " + product.getId() + " has insufficient quantity");
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }

    public ProductResponse findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new ProductNotFoundException("product with id " + id + " not found"));
    }
    
    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .toList();
    }

}
