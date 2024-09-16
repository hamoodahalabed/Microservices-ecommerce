package com.mohammad.camunda.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", url = "http://localhost/api/v1/orders")
@Component
public interface OrderClient {

    @PostMapping("/save")
    public ResponseEntity<Integer> SaveOrder(@RequestBody OrderDTO orderRequest);
}
