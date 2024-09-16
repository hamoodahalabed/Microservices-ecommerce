package com.mohammad.camunda.orderline;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "orderLine-service", url = "http://localhost/api/v1/order-lines")
public interface OrderLineClient {
    @PostMapping
    ResponseEntity<String> saveOrder (@RequestBody OrderLineRequest request);
}
