package com.mohammad.ecommerce.orderline;


public record OrderLineResponse(
        Integer id,
        double quantity
) { }