package com.mohammad.camunda.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
@ExternalTaskSubscription(topicName = "product")
 public class ProductService implements ExternalTaskHandler {
//public class ProductService implements JavaDelegate {

    private final ProductClient productClient;


    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        BigDecimal totalAmount = BigDecimal.ZERO;


        String productsObject = externalTask.getVariable("products");

        ObjectMapper objectMapper = new ObjectMapper();

        List<ProductPurchaseRequest> productPurchaseRequests;
        try {
            productPurchaseRequests = objectMapper.readValue(
                    productsObject,
                    new TypeReference<>() {
                    }
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<List<ProductPurchaseResponse>> ppr = productClient.purchaseProducts(productPurchaseRequests);

        if (ppr.getBody() != null) {
            for (ProductPurchaseResponse productPurchaseResponse : ppr.getBody()) {
                BigDecimal amountForProduct = productPurchaseResponse.price().multiply(BigDecimal.valueOf(productPurchaseResponse.quantity()));
                totalAmount = totalAmount.add(amountForProduct);
            }
        }

        String productsJson;
        try {
            productsJson = objectMapper.writeValueAsString(ppr.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        VariableMap resultVariables = Variables.createVariables();
        resultVariables.put("productsRes", productsJson);
        resultVariables.put("totalAmount", totalAmount);
        System.out.println("Purchase Product ...");
        externalTaskService.complete(externalTask, resultVariables);


    }
}
/*
  @Override
   public void execute(DelegateExecution delegateExecution) throws Exception {
        BigDecimal totalAmount = BigDecimal.ZERO;

        String productsObject = (String) delegateExecution.getVariable("products");

        ObjectMapper objectMapper = new ObjectMapper();

        List<ProductPurchaseRequest> productPurchaseRequests = objectMapper.readValue(
                productsObject,
                new TypeReference<List<ProductPurchaseRequest>>() {
                }
        );

        ResponseEntity<List<ProductPurchaseResponse>> ppr = productClient.purchaseProducts(productPurchaseRequests);

        if (ppr.getBody() != null) {
            for (ProductPurchaseResponse productPurchaseResponse : ppr.getBody()) {
                BigDecimal amountForProduct = productPurchaseResponse.price().multiply(BigDecimal.valueOf(productPurchaseResponse.quantity()));
                totalAmount = totalAmount.add(amountForProduct);
            }
        }

        String productsJson = objectMapper.writeValueAsString(ppr.getBody());
        delegateExecution.setVariable("productsRes", productsJson);

        delegateExecution.setVariable("totalAmount", totalAmount);

        System.out.println("Purchase Product ...");

    }
* */