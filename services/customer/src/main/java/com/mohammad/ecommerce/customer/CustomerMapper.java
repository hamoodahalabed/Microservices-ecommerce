package com.mohammad.ecommerce.customer;

import org.hibernate.validator.constraintvalidators.RegexpURLValidator;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public Customer toCustomer(CustomerRequest customerRequest) {

        if (customerRequest == null)
            return null;

        return Customer.builder()
                .id(customerRequest.id())
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .address(customerRequest.address())
                .build();
    }


    public CustomerResponse fromCustomer(Customer customer) {
        if (customer == null)
            return null;

        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
