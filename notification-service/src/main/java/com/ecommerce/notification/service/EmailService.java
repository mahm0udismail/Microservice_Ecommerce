package com.ecommerce.notification.service;

import com.ecommerce.notification.dto.OrderNotificationRequest;
import com.ecommerce.notification.dto.PaymentNotificationRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_RELATED;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendOrderConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            OrderNotificationRequest order
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_RELATED, UTF_8.name());
        messageHelper.setFrom("noreply@ecommerce.com");
        messageHelper.setTo(destinationEmail);
        messageHelper.setSubject("Order Confirmation - " + orderReference);

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("orderReference", orderReference);
        variables.put("totalAmount", amount);
        variables.put("products", order.products());

        Context context = new Context();
        context.setVariables(variables);
        String html = templateEngine.process("order-confirmation", context);
        messageHelper.setText(html, true);

        mailSender.send(mimeMessage);
        log.info("Order confirmation email sent to {} for order {}", destinationEmail, orderReference);
    }

    @Async
    public void sendPaymentConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_RELATED, UTF_8.name());
        messageHelper.setFrom("noreply@ecommerce.com");
        messageHelper.setTo(destinationEmail);
        messageHelper.setSubject("Payment Confirmation - " + orderReference);

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("orderReference", orderReference);
        variables.put("amount", amount);

        Context context = new Context();
        context.setVariables(variables);
        String html = templateEngine.process("payment-confirmation", context);
        messageHelper.setText(html, true);

        mailSender.send(mimeMessage);
        log.info("Payment confirmation email sent to {} for order {}", destinationEmail, orderReference);
    }
}
