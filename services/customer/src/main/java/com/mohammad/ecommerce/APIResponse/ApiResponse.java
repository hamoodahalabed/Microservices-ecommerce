package com.mohammad.ecommerce.APIResponse;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int status;
    private String message;
    private String service = "CUSTOMER-SERVICE";
    private T data;

    public ApiResponse(int status, String message, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public ApiResponse(ApiStatus apiStatus) {
        this.status = apiStatus.getStatusCode();
        this.message = apiStatus.getMessage();
        this.data = null;
    }


}
