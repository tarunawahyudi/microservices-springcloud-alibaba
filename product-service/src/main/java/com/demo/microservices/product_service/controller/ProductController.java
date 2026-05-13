package com.demo.microservices.product_service.controller;

import com.demo.microservices.common.dto.ProductResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return new ProductResponse(id, "MacBook Pro", 25000000L, 10);
    }
}
