package com.demo.microservices.order.controller;

import com.demo.microservices.common.dto.*;
import com.demo.microservices.common.event.OrderCreatedEvent;
import com.demo.microservices.order.client.ProductClient;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final ProductClient productClient;
    private final StreamBridge streamBridge;
    private final AtomicLong idGenerator = new AtomicLong(1);

    public OrderController(ProductClient productClient, StreamBridge streamBridge) {
        this.productClient = productClient;
        this.streamBridge = streamBridge;
    }

    @PostMapping
    public String createOrder(@RequestBody CreateOrderRequest request) {
        ProductResponse product = productClient.findById(request.productId());
        System.out.println("product nih: " + product.toString());

        if (product.stock() < request.quantity()) {
            return "Product unavailable";
        }

        Long orderId = idGenerator.getAndIncrement();
        Long totalPrice = product.price() * request.quantity();

        OrderCreatedEvent event = new OrderCreatedEvent(
            orderId,
            product.id(),
            product.name(),
            totalPrice
        );

        streamBridge.send("orderCreated-out-0", event);

        return "Order created: " + orderId;
    }
}