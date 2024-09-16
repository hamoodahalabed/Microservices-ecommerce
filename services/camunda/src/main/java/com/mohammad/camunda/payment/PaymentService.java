package com.mohammad.camunda.payment;

import com.mohammad.camunda.order.PaymentMethod;
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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
@ExternalTaskSubscription(topicName = "payment")
public class PaymentService implements ExternalTaskHandler {

    private final PaymentClient paymentClient;

    @Override
    public void execute (ExternalTask externalTask, ExternalTaskService externalTaskService) {

    PaymentMethod paymentMethod = externalTask.getVariable("paymentMethod");
    Integer orderId = externalTask.getVariable("orderId");
    String reference = externalTask.getVariable("reference");

    String id = externalTask.getVariable("customerId");
    String email = externalTask.getVariable("email");
    String firstName = externalTask.getVariable("firstName");
    String lastName = externalTask.getVariable("lastName");


    BigDecimal amount = externalTask.getVariable("totalAmount");

    ResponseEntity<Integer> payment =  paymentClient.createPayment(new PaymentRequest(amount,paymentMethod,orderId,reference,new CustomerResponse(id,firstName,lastName,email)));

        VariableMap resultVariables = Variables.createVariables();
        externalTaskService.complete(externalTask, resultVariables);
    System.out.println("Process Payment ...");
}






}

//@Override
//public void execute (DelegateExecution delegateExecution) throws Exception {
//
//    PaymentMethod paymentMethod = (PaymentMethod) delegateExecution.getVariable("paymentMethod");
//    Integer orderId = (Integer) delegateExecution.getVariable("orderId");
//    String reference = (String) delegateExecution.getVariable("reference");
//
//    String id = (String) delegateExecution.getVariable("customerId");
//    String email = (String) delegateExecution.getVariable("email");
//    String firstName = (String) delegateExecution.getVariable("firstName");
//    String lastName = (String) delegateExecution.getVariable("lastName");
//
//
//    BigDecimal amount = (BigDecimal) delegateExecution.getVariable("totalAmount");
//
//    ResponseEntity<Integer> payment =  paymentClient.createPayment(new PaymentRequest(amount,paymentMethod,orderId,reference,new CustomerResponse(id,firstName,lastName,email)));
//
//    System.out.println("Process Payment ...");
//}