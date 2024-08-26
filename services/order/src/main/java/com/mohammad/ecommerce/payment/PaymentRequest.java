package com.mohammad.ecommerce.payment;

import com.mohammad.ecommerce.customer.CustomerResponse;
import com.mohammad.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal totAmount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}