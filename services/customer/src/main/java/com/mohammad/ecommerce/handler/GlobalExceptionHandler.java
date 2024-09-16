package com.mohammad.ecommerce.handler;


import com.mohammad.ecommerce.APIResponse.ApiStatus;
import com.mohammad.ecommerce.APIResponse.ApiResponse;
import com.mohammad.ecommerce.exception.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(CustomerNotFoundException.class)
//    public ResponseEntity<String> handle (CustomerNotFoundException exp) {
//
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(exp.getMessage());
//    }

//    @ExceptionHandler(CustomerNotFoundException.class)
//    public ResponseEntity<ApiResponse<CustomerResponse>> handle (CustomerNotFoundException exp) {
//
//        ApiResponse<CustomerResponse> response = new ApiResponse<>(HttpStatus.NOT_FOUND.name(),exp.getMessage(),null);
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(response);
//    }
@ExceptionHandler(CustomerNotFoundException.class)
public ResponseEntity<ApiResponse<Object>> handleCustomerNotFoundException(CustomerNotFoundException ex) {
    ApiResponse<Object> response = new ApiResponse<>(ApiStatus.CUSTOMER_NOT_FOUND);
    return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .body(response);
}

@ExceptionHandler(Exception.class)
public ResponseEntity<ApiResponse<Object>> handleGlobalException(Exception e) {

    ApiResponse<Object> response = new ApiResponse<>(ApiStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
}


//    @ExceptionHandler(CustomerNotFoundException.class)
//    public void handle(CustomerNotFoundException ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
//
//        logRequestData(request);
//
//        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//
//        response.setContentType("application/json");
//
//        ApiResponseObj apiResponse = new ApiResponseObj(
//                String.valueOf(HttpServletResponse.SC_NOT_FOUND),
//                ex.getMessage(),
//                null
//        );
//
//        PrintWriter out = response.getWriter();
//        out.print(new ObjectMapper().writeValueAsString(apiResponse));
//    }

//    @ExceptionHandler(CustomerNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String handle (CustomerNotFoundException exp) {
//
//        return exp.getMessage();
//    }

//    private void logRequestData(HttpServletRequest request) {
//        System.out.println("Request URL: " + request.getRequestURL().toString());
//
//        System.out.println("Request URI: " + request.getRequestURI());
//
//        System.out.println("Request Method: " + request.getMethod());
//
//        System.out.println("Request Headers:");
//        request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
//            System.out.println(headerName + ": " + request.getHeader(headerName));
//        });
//
//        System.out.println("Request Parameters:");
//        request.getParameterMap().forEach((key, value) -> {
//            System.out.println(key + ": " + String.join(", ", value));
//        });
//    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle (MethodArgumentNotValidException exp) {

        var erros = new HashMap<String,String>();
        exp.getBindingResult().getAllErrors().forEach((error) -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            erros.put(fieldName, errorMessage);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(erros));
    }


}
