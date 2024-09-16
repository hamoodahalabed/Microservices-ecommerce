package com.mohammad.camunda.payment;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email
) {

}