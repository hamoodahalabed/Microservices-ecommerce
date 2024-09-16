package com.mohammad.ecommerce.customer;

import com.mohammad.ecommerce.APIResponse.ApiResponse;
import com.mohammad.ecommerce.APIResponse.ApiResponseObj;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    @Value("${server.port}")
    private int serverPort;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createCustomer (
            @RequestBody @Valid CustomerRequest customerRequest
    ) {
        System.out.println("server port + " + serverPort);
        String data = customerService.createCustomer(customerRequest);
        return ResponseEntity.ok(new ApiResponse<>(201,"Added successfully", data));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateCustomer(
            @RequestBody @Valid CustomerRequest customerRequest
    ) {
        String data = customerService.updateCustomer(customerRequest);

        return ResponseEntity.accepted().body(new ApiResponse<String>(201, "Updated successfully", data));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> FindAll() {
        var data = customerService.findAllCustomer();
        ApiResponse<List<CustomerResponse>> response = new ApiResponse<List<CustomerResponse>> (200,"The data found", data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exist/{customer-id}")
    public ResponseEntity<ApiResponse<Boolean>> existById (@PathVariable("customer-id") String customerId) {

       Boolean Data = customerService.existById(customerId);
       ApiResponse<Boolean> response = new ApiResponse<>(200,"founded", Data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{customer-id}")
    public  ApiResponse<CustomerResponse>  findById (@PathVariable("customer-id") String customerId) {
        var data =  customerService.findById(customerId);

        ApiResponse<CustomerResponse> response = new ApiResponse<CustomerResponse> (200,"The data found", data);
        return response;
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<ApiResponse<String>> delete (
            @PathVariable("customer-id") String customerId
    ) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.accepted().body(new ApiResponse<String>(200, "Deleted successfully", customerId));
    }

//    @GetMapping("/structure/{customer-id}")
//    public ResponseEntity<ApiResponse<CustomerResponse>> findByIdStructured (@PathVariable("customer-id") String customerId) {
//
//       var data =  customerService.findById(customerId);
//
//        ApiResponse<CustomerResponse> response = new ApiResponse<CustomerResponse> ("accept","the data found", data);
//
//        return ResponseEntity.ok(response);
//
//    }

    @GetMapping("/structure/{customer-id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> findByIdStructured (@PathVariable("customer-id") String customerId) {

        var data =  customerService.findById(customerId);

        ApiResponse<CustomerResponse> response = new ApiResponse<CustomerResponse> (200,"The data found", data);
        return ResponseEntity.ok(response);

    }

}
