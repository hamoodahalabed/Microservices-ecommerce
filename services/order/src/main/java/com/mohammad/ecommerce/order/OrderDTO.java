package com.mohammad.ecommerce.order;

import java.math.BigDecimal;

public record OrderDTO (
        Integer id,
        String reference,
        PaymentMethod paymentMethod,
        String customerId,
        BigDecimal amount
) {
}
