package com.ecommerce.order.service;

import com.ecommerce.order.client.CustomerClient;
import com.ecommerce.order.client.NotificationClient;
import com.ecommerce.order.client.PaymentClient;
import com.ecommerce.order.client.ProductClient;
import com.ecommerce.order.dto.*;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.repository.OrderLineRepository;
import com.ecommerce.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final NotificationClient notificationClient;
    private final OrderMapper mapper;

    @Transactional
    public Integer createOrder(OrderRequest request) {

        // 1. Validate customer exists
        CustomerResponse customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cannot create order: no customer found with id " + request.customerId()
                ));

        // 2. Purchase products (validates stock and reduces quantity)
        List<PurchaseResponse> purchasedProducts = productClient.purchaseProducts(
                request.products().stream()
                        .map(p -> new PurchaseRequest(p.productId(), p.quantity()))
                        .collect(Collectors.toList())
        );

        // 3. Save the order
        Order order = orderRepository.save(mapper.toOrder(request));

        // 4. Save order lines
        request.products().forEach(orderLineRequest ->
                orderLineRepository.save(mapper.toOrderLine(orderLineRequest, order))
        );

        // 5. Process payment (calls payment-service synchronously)
        paymentClient.requestOrderPayment(new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                request.reference(),
                customer
        ));

        // 6. Send order confirmation notification
        notificationClient.sendOrderNotification(new OrderNotificationRequest(
                request.reference(),
                request.amount(),
                customer.email(),
                customer.firstname(),
                customer.lastname(),
                purchasedProducts
        ));

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::toOrderResponse)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No order found with id: " + orderId
                ));
    }
}
