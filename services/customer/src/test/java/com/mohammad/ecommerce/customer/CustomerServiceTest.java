package com.mohammad.ecommerce.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Address address = new Address(
                "street",
                "houseNumber",
                "zipCode"
        );


    }

    @Test
    public void shouldSuccessfullyCreateCustomer() {
        // given
        Address address = new Address(
                "street",
                "houseNumber",
                "zipCode"
        );
        CustomerRequest customerRequest = new CustomerRequest(
                "159",
                "test_first_name",
                "test_last_name",
                "test_email@gmail.com",
                address
        );
        Customer customer = new Customer(
                "159",
                "test_first_name",
                "test_last_name",
                "test_email@gmail.com",
                address
        );

        Mockito.when(mapper.toCustomer(customerRequest)).thenReturn(customer);
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);

        // when
        String customerId = customerService.createCustomer(customerRequest);

        // then
        assertEquals(customerRequest.id(), customerId);
    }

    @Test
    public void shouldUpdateCustomerSuccessfully() {
        // given
        Address address = new Address(
                "street",
                "houseNumber",
                "zipCode"
        );
        CustomerRequest customerRequest = new CustomerRequest(
                "123", // Matching ID for existingCustomer
                "Jane",
                "Doe",
                "jane.doe@example.com",
                address
        );

        Customer existingCustomer = new Customer(
                "123",
                "Jane",
                "Doe",
                "jane.doe@example.com",
                address
        );

        Mockito.when(customerRepository.findById(customerRequest.id())).thenReturn(Optional.of(existingCustomer));
        Mockito.when(customerRepository.save(existingCustomer)).thenReturn(existingCustomer);

        // when
        String updatedCustomerId = customerService.updateCustomer(customerRequest);

        // then
        assertEquals(customerRequest.id(), updatedCustomerId);
    }


    @Test
    public void shouldMergeCustomer () {
        // given
        Address address = new Address(
                "street",
                "houseNumber",
                "zipCode"
        );
        CustomerRequest customerRequest = new CustomerRequest(
                "123", // Matching ID for existingCustomer
                "Jane",
                "Doe",
                "jane.doe@example.com",
                address
        );

        Customer customer = new Customer(
                "123",
                "Jane",
                "Doe",
                "jane.doe@example.com",
                address
        );

        //when
        customerService.mergerCustomer(customer, customerRequest);

        //then

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
    void shouldFindAllCustomers() {
        // Arrange
        Address address = new Address(
                "street",
                "houseNumber",
                "zipCode"
        );
        Customer customer1 = new Customer("1", "John","Doe", "john@example.com",address);
        Customer customer2 = new Customer("2", "Jane","Doe", "jane@example.com",address);

        List<Customer> customers = Arrays.asList(customer1, customer2);

        CustomerResponse response1 = new CustomerResponse("1", "John","Doe", "john@example.com",address);
        CustomerResponse response2 = new CustomerResponse("2", "Jane","Doe", "Jane@example.com",address);

        Mockito.when(customerRepository.findAll()).thenReturn(customers);
        Mockito.when(mapper.fromCustomer(customer1)).thenReturn(response1);
        Mockito.when(mapper.fromCustomer(customer2)).thenReturn(response2);

        // Act
        List<CustomerResponse> responses = customerService.findAllCustomer();

        // Assert
        assertEquals(2, responses.size());
        assertEquals(response1, responses.get(0));
        assertEquals(response2, responses.get(1));
    }

    @Test
    public void shouldFindExistByIdSuccessfully() {
        Address address = new Address(
                "street",
                "houseNumber",
                "zipCode"
        );
        Customer customer1 = new Customer("1", "John","Doe", "john@example.com",address);

        Mockito.when(customerRepository.findById(customer1.getId())).thenReturn(Optional.of(customer1));

        Boolean exist = customerService.existById(customer1.getId());
        assertEquals(true,exist);
    }

    @Test
    public void shouldDeleteCustomerSuccessfully() {
        String customerId = "123";

        Address address = new Address(
                "street",
                "houseNumber",
                "zipCode"
        );
        Customer customer1 = new Customer("123", "John","Doe", "john@example.com",address);

        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer1));

       String cid =  customerService.deleteCustomer(customerId);

       assertEquals(cid, customer1.getId());

    }
}
