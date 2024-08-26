package com.mohammad.ecommerce.kafka;

import com.mohammad.ecommerce.customer.CustomerResponse;
import com.mohammad.ecommerce.order.PaymentMethod;
import com.mohammad.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation (
        String orderReference,
        BigDecimal totAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {}