package com.mohammad.camunda.camunda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mohammad.camunda.order.OrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.mohammad.camunda.camunda.ProcessId.ORDER_PROCESS;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/v1/camunda")
@RequiredArgsConstructor
public class CamundaController {

    private final  ProcessService processService;
    private final ObjectMapper objectMapper;

    @PostMapping("/create-order")
    public String startOrderProcess(@RequestBody @Valid OrderRequest orderRequest) throws JsonProcessingException {
        String productsJson = objectMapper.writeValueAsString(orderRequest.products());
        Map<String, Object> variables = new HashMap<>();

        variables.put("customerId", orderRequest.customerId());
        variables.put("reference", orderRequest.reference());
        variables.put("paymentMethod", orderRequest.paymentMethod());
        variables.put("products", productsJson);

        processService.startProcessByIdWithVariables(ORDER_PROCESS,variables);
        return "order created successfully !";
    }
}
