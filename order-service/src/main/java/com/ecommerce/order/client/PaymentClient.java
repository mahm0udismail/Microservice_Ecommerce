package com.ecommerce.order.client;

import com.ecommerce.order.dto.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "${application.config.payment-url:}")
public interface PaymentClient {

    @PostMapping("/api/v1/payments")
    Integer requestOrderPayment(@RequestBody PaymentRequest paymentRequest);
}
