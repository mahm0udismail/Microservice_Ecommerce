package com.ecommerce.notification.controller;

import com.ecommerce.notification.dto.OrderNotificationRequest;
import com.ecommerce.notification.dto.PaymentNotificationRequest;
import com.ecommerce.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping("/order")
    public ResponseEntity<Void> sendOrderNotification(@RequestBody OrderNotificationRequest request) {
        service.handleOrderNotification(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/payment")
    public ResponseEntity<Void> sendPaymentNotification(@RequestBody PaymentNotificationRequest request) {
        service.handlePaymentNotification(request);
        return ResponseEntity.accepted().build();
    }
}
