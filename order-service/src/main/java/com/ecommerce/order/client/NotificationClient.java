package com.ecommerce.order.client;

import com.ecommerce.order.dto.OrderNotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "${application.config.notification-url:}")
public interface NotificationClient {

    @PostMapping("/api/v1/notifications/order")
    void sendOrderNotification(@RequestBody OrderNotificationRequest request);
}
