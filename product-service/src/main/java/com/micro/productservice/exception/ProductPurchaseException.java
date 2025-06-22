package com.micro.productservice.exception;

public class ProductPurchaseException extends RuntimeException {
    public ProductPurchaseException(String message) {
        super(message);
    }

    public ProductPurchaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
