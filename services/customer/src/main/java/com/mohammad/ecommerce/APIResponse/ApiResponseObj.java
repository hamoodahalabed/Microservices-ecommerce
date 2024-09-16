package com.mohammad.ecommerce.APIResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseObj {

    String status;
    String message;
    Object data;
}
