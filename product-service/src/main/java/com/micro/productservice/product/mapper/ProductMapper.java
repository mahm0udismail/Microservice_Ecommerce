package com.micro.productservice.product.mapper;

import com.micro.productservice.product.dto.ProductPurchaseResponse;
import com.micro.productservice.product.dto.ProductRequest;
import com.micro.productservice.product.model.ProductResponse;

import org.springframework.stereotype.Service;

import com.micro.productservice.product.category.Category;
import com.micro.productservice.product.model.Product;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .availableQuantity(request.availableQuantity())
                .price(request.price())
                .category(
                        Category.builder()
                                .id(request.categoryId())
                                .build()
                )
                .build();
    }
    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                quantity,
                product.getPrice()
        );
    }

}
