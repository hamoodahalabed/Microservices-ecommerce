package com.mohammad.camunda.kafka;

import com.mohammad.camunda.order.PaymentMethod;
import com.mohammad.camunda.order.PurchaseResponse;
import com.mohammad.camunda.payment.CustomerResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation (
        String orderReference,
        BigDecimal totAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {}