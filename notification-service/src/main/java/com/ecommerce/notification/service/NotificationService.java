package com.ecommerce.notification.service;

import com.ecommerce.notification.dto.OrderNotificationRequest;
import com.ecommerce.notification.dto.PaymentNotificationRequest;
import com.ecommerce.notification.model.Notification;
import com.ecommerce.notification.model.NotificationType;
import com.ecommerce.notification.repository.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository repository;
    private final EmailService emailService;

    public void handleOrderNotification(OrderNotificationRequest request) {
        // Persist notification
        repository.save(Notification.builder()
                .type(NotificationType.ORDER_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .orderReference(request.orderReference())
                .recipientEmail(request.customerEmail())
                .recipientFirstname(request.customerFirstname())
                .recipientLastname(request.customerLastname())
                .content("Order confirmed: " + request.orderReference())
                .build()
        );

        // Send email
        String customerName = request.customerFirstname() + " " + request.customerLastname();
        try {
            emailService.sendOrderConfirmationEmail(
                    request.customerEmail(),
                    customerName,
                    request.totalAmount(),
                    request.orderReference(),
                    request
            );
        } catch (MessagingException e) {
            log.error("Failed to send order confirmation email for order {}: {}",
                    request.orderReference(), e.getMessage());
        }
    }

    public void handlePaymentNotification(PaymentNotificationRequest request) {
        // Persist notification
        repository.save(Notification.builder()
                .type(NotificationType.PAYMENT_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .orderReference(request.orderReference())
                .recipientEmail(request.customerEmail())
                .recipientFirstname(request.customerFirstname())
                .recipientLastname(request.customerLastname())
                .content("Payment confirmed for order: " + request.orderReference())
                .build()
        );

        // Send email
        String customerName = request.customerFirstname() + " " + request.customerLastname();
        try {
            emailService.sendPaymentConfirmationEmail(
                    request.customerEmail(),
                    customerName,
                    request.amount(),
                    request.orderReference()
            );
        } catch (MessagingException e) {
            log.error("Failed to send payment confirmation email for order {}: {}",
                    request.orderReference(), e.getMessage());
        }
    }
}
