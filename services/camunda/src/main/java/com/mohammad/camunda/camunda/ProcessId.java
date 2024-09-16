package com.mohammad.camunda.camunda;

import lombok.Getter;

@Getter
public enum ProcessId {
    ORDER_PROCESS("orderprocess");

    private final String id;

    ProcessId(String id) {
        this.id = id;
    }


}
