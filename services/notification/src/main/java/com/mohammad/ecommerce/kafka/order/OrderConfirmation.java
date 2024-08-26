package com.mohammad.ecommerce.kafka.order;

import com.mohammad.ecommerce.kafka.payment.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totAmount,
        PaymentMethod paymentMethod,
        Customer customer,
        List<Product> products

) {
}