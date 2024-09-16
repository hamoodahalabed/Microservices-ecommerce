package com.mohammad.ecommerce.order;

import org.springframework.stereotype.Service;

@Service
public class OrderMapper {


    public Order toOrder(OrderRequest request) {
        if (request == null) {
            return null;
        }
        return Order.builder()
                .id(request.id())
                .reference(request.reference())
                .paymentMethod(request.paymentMethod())
                .customerId(request.customerId())
                .build();
    }

    public OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }

    public Order toOrder(OrderDTO orderDTO) {

        if (orderDTO == null) {
            return null;
        }
        return Order.builder()
                .id(orderDTO.id())
                .reference(orderDTO.reference())
                .paymentMethod(orderDTO.paymentMethod())
                .customerId(orderDTO.customerId())
                .totalAmount(orderDTO.amount())
                .build();
    }
}