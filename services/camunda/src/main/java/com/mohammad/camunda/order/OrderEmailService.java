package com.mohammad.camunda.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohammad.camunda.kafka.OrderConfirmation;
import com.mohammad.camunda.kafka.OrderProducer;
import com.mohammad.camunda.payment.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service

@ExternalTaskSubscription(topicName = "OrderEmailService")
public class OrderEmailService implements ExternalTaskHandler {
//public class OrderEmailService implements JavaDelegate {

    private final OrderProducer orderProducer;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {

        String reference = externalTask.getVariable("reference");
        PaymentMethod paymentMethod = externalTask.getVariable("paymentMethod");
        String id = externalTask.getVariable("customerId");
        String email = externalTask.getVariable("email");
        String firstName = externalTask.getVariable("firstName");
        String lastName = externalTask.getVariable("lastName");

        CustomerResponse customer = new CustomerResponse(id,firstName,lastName,email);

        String productsObject = externalTask.getVariable("productsRes");

        ObjectMapper objectMapper = new ObjectMapper();

        List<PurchaseResponse> purchaseResponse = null;
        try {
            purchaseResponse = objectMapper.readValue(
                    productsObject,
                    new TypeReference<List<PurchaseResponse>>() {
                    }
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        BigDecimal amount = externalTask.getVariable("totalAmount");

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        reference,
                        amount,
                        paymentMethod,
                        customer,
                        purchaseResponse
                ));

        VariableMap resultVariables = Variables.createVariables();
        System.out.println("order confirmation sent");
        externalTaskService.complete(externalTask, resultVariables);
    }
}
