package com.mohammad.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@EqualsAndHashCode(callSuper = true)
//@Data
//@ResponseStatus(HttpStatus.OK)
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException (String msg) {
        super(msg);
    }
    //private final String msg;
}
