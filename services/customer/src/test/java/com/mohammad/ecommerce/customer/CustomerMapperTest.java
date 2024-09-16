package com.mohammad.ecommerce.customer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    private CustomerMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CustomerMapper();
    }

    @Test
    public void shouldMapCustomerRequestToCustomer() {
        Address address = new Address(
                "Jama'a street",
                "321",
                "113"
        );
        CustomerRequest customerRequest = new CustomerRequest(
                "123",
                "Mohammad",
                "Alabed",
                "mohammad.alabed@gmail.com",
                address);

        Customer customer = mapper.toCustomer(customerRequest);

        assertEquals(customerRequest.id(), customer.getId());
        assertEquals(customerRequest.firstName(), customer.getFirstName());
        assertEquals(customerRequest.lastName(), customer.getLastName());
        assertEquals(customerRequest.email(), customer.getEmail());

        assertNotNull(customer.getAddress());
        assertEquals(customerRequest.address().getStreet(), customer.getAddress().getStreet());
        assertEquals(customerRequest.address().getHouseNumber(), customer.getAddress().getHouseNumber());
        assertEquals(customerRequest.address().getZipCode(), customer.getAddress().getZipCode());

    }

    @Test
    public void shouldMapCustomerRequestToCustomerWhenCustomerRequestIsNull () {
        Customer customer = mapper.toCustomer(null);
        assertNull(customer,"Customer should be null when CustomerRequest is null");
    }

    @Test
    public void shouldMapCustomerToCustomerResponse() {
        Address address = new Address(
                "madina street",
                "111",
                "001"
        );
        Customer customer = new Customer("000",
                "samin",
                "oranm","sami@gmail.com",
                address);

        CustomerResponse customerResponse = mapper.fromCustomer(customer);

        assertEquals(customerResponse.id(), customer.getId());
        assertEquals(customerResponse.firstName(), customer.getFirstName());
        assertEquals(customerResponse.lastName(), customer.getLastName());
        assertEquals(customerResponse.email(), customer.getEmail());

        assertNotNull(customer.getAddress());
        assertEquals(customerResponse.address().getStreet(), customer.getAddress().getStreet());
        assertEquals(customerResponse.address().getHouseNumber(), customer.getAddress().getHouseNumber());
        assertEquals(customerResponse.address().getZipCode(), customer.getAddress().getZipCode());

    }


    @Test
    public void shouldMapCustomerToCustomerResponseWhenCustomerIsNull () {
        CustomerResponse customerResponse = mapper.fromCustomer(null);
        assertNull(customerResponse,"customerResponse should be null when Customer is null");
    }
}