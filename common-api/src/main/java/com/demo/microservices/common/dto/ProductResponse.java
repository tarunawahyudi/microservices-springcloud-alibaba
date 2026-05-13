package com.demo.microservices.common.dto;

public record ProductResponse(
        Long id,
        String name,
        Long price,
        Integer stock
) {}
