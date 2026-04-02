package com.ecommerce.payment.service;

import com.ecommerce.payment.client.NotificationClient;
import com.ecommerce.payment.dto.PaymentNotificationRequest;
import com.ecommerce.payment.dto.PaymentRequest;
import com.ecommerce.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationClient notificationClient;

    public Integer createPayment(PaymentRequest request) {
        var payment = repository.save(mapper.toPayment(request));

        notificationClient.sendPaymentNotification(new PaymentNotificationRequest(
                request.orderReference(),
                request.amount(),
                request.paymentMethod(),
                request.customer().email(),
                request.customer().firstname(),
                request.customer().lastname()
        ));

        return payment.getId();
    }
}
