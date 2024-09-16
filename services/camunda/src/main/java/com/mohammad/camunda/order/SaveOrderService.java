package com.mohammad.camunda.order;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
@ExternalTaskSubscription(topicName = "SaveOrderService")
public class SaveOrderService implements ExternalTaskHandler {
//public class SaveOrderService implements JavaDelegate {

    private final OrderClient orderClient;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService){


        String reference =  externalTask.getVariable("reference");
        PaymentMethod paymentMethod =  externalTask.getVariable("paymentMethod");
        String customerId =  externalTask.getVariable("customerId");
        BigDecimal amount =  externalTask.getVariable("totalAmount");


       OrderDTO order = new OrderDTO(null,reference,paymentMethod,customerId,amount);
       Integer OrderId =  orderClient.SaveOrder(order).getBody();



        VariableMap resultVariables = Variables.createVariables();
        resultVariables.put("orderId", OrderId);
        System.out.println("Saving Order ...");
        externalTaskService.complete(externalTask, resultVariables);

    }
}
