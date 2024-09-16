package com.mohammad.ecommerce.customer;

import com.mohammad.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest customerRequest) {

        var customer = customerRepository.save(mapper.toCustomer(customerRequest));
        return customer.getId();
    }

    public String updateCustomer(CustomerRequest customerRequest) {

        var customer = customerRepository.findById(customerRequest.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot update customer:: No customer founded with provided ID:: %s ", customerRequest.id())
                ));
        mergerCustomer(customer,customerRequest);
        return customerRepository.save(customer).getId();
    }

    public void mergerCustomer(Customer customer, CustomerRequest customerRequest) {
        if(StringUtils.isNotBlank(customerRequest.firstName())) {
            customer.setFirstName(customerRequest.firstName());
        }
        if(StringUtils.isNotBlank(customerRequest.lastName())) {
            customer.setLastName(customerRequest.lastName());
        }
        if(StringUtils.isNotBlank(customerRequest.email())) {
            customer.setEmail(customerRequest.email());
        }
        if(customerRequest.address() != null ) {
            customer.setAddress(customerRequest.address());
        }
    }

    public List<CustomerResponse> findAllCustomer() {
        return customerRepository.findAll()
                .stream()
                .map(mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existById(String customerId) {
        return customerRepository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {

        return customerRepository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer found with ID:: %s " , customerId)));
    }

    public String deleteCustomer(String customerId) {

        var customer = customerRepository.findById(customerId)
                        .orElseThrow(() -> new CustomerNotFoundException("No customer found with ID:: %s " + customerId));
        customerRepository.deleteById(customerId);

        return customer.getId();
    }
}
