package com.mohammad.ecommerce.order;

import com.mohammad.ecommerce.kafka.OrderConfirmation;
import com.mohammad.ecommerce.customer.CustomerClient;
import com.mohammad.ecommerce.exception.BusinessException;
import com.mohammad.ecommerce.kafka.OrderProducer;
import com.mohammad.ecommerce.orderline.OrderLineRequest;
import com.mohammad.ecommerce.orderline.OrderLineService;
import com.mohammad.ecommerce.payment.PaymentClient;
import com.mohammad.ecommerce.payment.PaymentRequest;
import com.mohammad.ecommerce.product.ProductClient;
import com.mohammad.ecommerce.product.ProductFeignClient;
import com.mohammad.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final ProductFeignClient productFeignClient;
    BigDecimal totAmount = BigDecimal.ZERO;

    @Transactional
    public Integer createOrder(OrderRequest request) {
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

        var purchasedProducts = productClient.purchaseProducts(request.products());

        var order = this.repository.save(mapper.toOrder(request));

        totAmount = BigDecimal.ZERO;

        for (PurchaseRequest purchaseRequest : request.products()) {

            var prod = productFeignClient.findById(purchaseRequest.productId()).getBody();

            totAmount = totAmount.add(prod.price().multiply(BigDecimal.valueOf(purchaseRequest.quantity())));

            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        var paymentRequest = new PaymentRequest(
                totAmount,
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        totAmount,
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }

    public Integer saveOrder(OrderDTO orderRequest) {
        var orderReq = repository.save(mapper.toOrder(orderRequest));

        return orderReq.getId();
    }
}