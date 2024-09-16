package com.mohammad.camunda.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "http://localhost/api/v1/customers")
@Component
public interface CustomerClient {

    @GetMapping("/{customerId}")
    ApiResponse getCustomer(@PathVariable("customerId") String customerId);
}
