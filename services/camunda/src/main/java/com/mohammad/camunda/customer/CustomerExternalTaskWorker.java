package com.mohammad.camunda.customer;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@ExternalTaskSubscription(topicName = "checkCustomer")
public class CustomerExternalTaskWorker implements ExternalTaskHandler {

    private final CustomerClient customerClient;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        try {
            // Retrieve variables from the external task
            String customerId = externalTask.getVariable("customerId");

            // Perform the logic to get the customer
            ApiResponse response = getCustomer(customerId);
            boolean customerExists = response.getStatus() == 200;

            // Prepare the result variables
            VariableMap resultVariables = Variables.createVariables();
            resultVariables.put("email", response.getData().email());
            resultVariables.put("firstName", response.getData().firstName());
            resultVariables.put("lastName", response.getData().lastName());
            resultVariables.put("customerFound", customerExists);


            System.out.println("Customer checked: " + customerId);
            // Complete the external task with the result variables
            externalTaskService.complete(externalTask, resultVariables);


        } catch (Exception e) {
            System.err.println("Error processing task: " + e.getMessage());
            externalTaskService.handleFailure(externalTask, e.getMessage(), e.getMessage(), 0, 0);
        }
    }

    private ApiResponse getCustomer(String customerId) {
        return customerClient.getCustomer(customerId);
    }
}
