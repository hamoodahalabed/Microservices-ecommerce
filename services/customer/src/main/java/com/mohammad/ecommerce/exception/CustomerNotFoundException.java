package com.mohammad.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

//@EqualsAndHashCode(callSuper = true)
//@Data
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException (String msg) {
        super(msg);
    }
    //private final String msg;
}
