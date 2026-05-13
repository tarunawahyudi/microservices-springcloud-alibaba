package com.demo.microservices.order.client;

import com.demo.microservices.common.dto.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient {

    @Override
    public ProductResponse findById(Long id) {
        return new ProductResponse(id, "UNKNOWN_PRODUCT", 0L, 0);
    }
}