package com.demo.microservices.common.event;

public record OrderCreatedEvent(
        Long orderId,
        Long productId,
        String productName,
        Long totalPrice
) {}
