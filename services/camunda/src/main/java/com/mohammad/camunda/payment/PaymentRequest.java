package com.mohammad.camunda.payment;

import com.mohammad.camunda.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal totAmount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}