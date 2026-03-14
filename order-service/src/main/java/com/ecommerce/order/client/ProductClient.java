package com.ecommerce.order.client;

import com.ecommerce.order.dto.PurchaseRequest;
import com.ecommerce.order.dto.PurchaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service", url = "${application.config.product-url:}")
public interface ProductClient {

    @PostMapping("/api/v1/products/purchase")
    List<PurchaseResponse> purchaseProducts(@RequestBody List<PurchaseRequest> requestBody);
}
