package com.mohammad.camunda.customer;

import com.mohammad.camunda.payment.CustomerResponse;
import lombok.Data;

@Data
public class ApiResponse {
    private int status;
    private String message;
    private String service;
    private CustomerResponse data;

}
