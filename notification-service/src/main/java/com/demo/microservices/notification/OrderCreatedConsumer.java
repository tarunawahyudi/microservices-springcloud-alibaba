package com.demo.microservices.notification;

import com.demo.microservices.common.event.OrderCreatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class OrderCreatedConsumer {

    @Bean
    public Consumer<OrderCreatedEvent> orderCreated() {
        return event -> {
            System.out.println("Send notification for order: " + event.orderId());
            System.out.println("Product: " + event.productName());
            System.out.println("Total: " + event.totalPrice());
        };
    }
}