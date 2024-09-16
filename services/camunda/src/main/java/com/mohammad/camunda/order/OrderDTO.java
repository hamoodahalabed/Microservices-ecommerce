package com.mohammad.camunda.order;

import java.math.BigDecimal;

public record OrderDTO (
        Integer id,
        String reference,
        PaymentMethod paymentMethod,
        String customerId,
        BigDecimal amount
) {
}
