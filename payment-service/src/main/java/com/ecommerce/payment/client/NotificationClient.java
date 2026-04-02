package com.ecommerce.payment.client;

import com.ecommerce.payment.dto.PaymentNotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "${application.config.notification-url:}")
public interface NotificationClient {

    @PostMapping("/api/v1/notifications/payment")
    void sendPaymentNotification(@RequestBody PaymentNotificationRequest request);
}
