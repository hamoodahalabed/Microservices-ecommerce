package com.mohammad.camunda.camunda;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final RuntimeService runtimeService;

    public void startProcessByIdWithVariables(ProcessId processId, Map<String, Object> variables) {

        String processDefinitionKey = processId.getId();

        ProcessInstance processInstance = runtimeService
                .createProcessInstanceByKey(processDefinitionKey)
                .setVariables(variables)
                .execute();
    }
}
