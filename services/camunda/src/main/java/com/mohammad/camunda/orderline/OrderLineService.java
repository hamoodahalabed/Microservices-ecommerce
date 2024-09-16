package com.mohammad.camunda.orderline;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohammad.camunda.order.PurchaseRequest;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@ExternalTaskSubscription(topicName = "OrderLineService")
public class OrderLineService implements ExternalTaskHandler {
//public class OrderLineService implements JavaDelegate {
    private final OrderLineClient orderLineClient;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService){

        String productString = externalTask.getVariable("products");

        ObjectMapper objectMapper = new ObjectMapper();

        List<PurchaseRequest> purchaseRequests = null;
        try {
            purchaseRequests = objectMapper.readValue(
                    productString,
                    new TypeReference<List<PurchaseRequest>>() {}
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Integer orderId = externalTask.getVariable("orderId");

        for (PurchaseRequest purchaseRequest : purchaseRequests) {

            orderLineClient.saveOrder(
                    new OrderLineRequest(
                            null,
                            orderId,
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );

        }
        VariableMap resultVariables = Variables.createVariables();
        System.out.println("Saving Order Line ...");
        externalTaskService.complete(externalTask, resultVariables);

    }


}
