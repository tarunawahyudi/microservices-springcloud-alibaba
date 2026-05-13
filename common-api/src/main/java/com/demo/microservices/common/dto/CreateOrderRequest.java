package com.demo.microservices.common.dto;

public record CreateOrderRequest(
        Long productId,
        Integer quantity
) {}