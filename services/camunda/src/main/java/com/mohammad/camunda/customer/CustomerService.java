package com.mohammad.camunda.customer;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService implements JavaDelegate {

    private final CustomerClient customerClient;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String customerId = (String) delegateExecution.getVariable("customerId");

        ApiResponse response = getCustomer(customerId);

        boolean customerExists = response.getStatus() == 200;

        if (customerExists) {
            delegateExecution.setVariable("email", response.getData().email());
            delegateExecution.setVariable("firstName", response.getData().firstName());
            delegateExecution.setVariable("lastName", response.getData().lastName());
        }
        delegateExecution.setVariable("customerFound", customerExists);

        System.out.println("Checking Customer: " + customerId);
        if (!customerExists) {
            System.out.println("Customer: " + customerId + " not found");
        }

    }

    public ApiResponse getCustomer(String customerId) {

        return customerClient.getCustomer(customerId);
    }
}
