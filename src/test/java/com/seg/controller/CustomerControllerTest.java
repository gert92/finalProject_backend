package com.seg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seg.constants.PersonType;
import com.seg.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.seg.service.impl.CustomerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;


    @Test
    void shouldCreateCustomer() throws Exception {
        Customer customer = new Customer("Eku", "Tamm", "Eku@mail.com", PersonType.ADULT, "A157");

        when(customerService.saveCustomer(customer)).thenReturn(customer);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnCustomerById() throws Exception {
        Customer customer = new Customer("Eku", "Tamm", "Eku@mail.com", PersonType.ADULT, "A157");

        when(customerService.getCustomerById(0L)).thenReturn(customer);

        mockMvc.perform(get("/api/users/{id}", 0L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Eku"));
    }

    @Test
    void shouldReturnAllCustomers() throws Exception {
        List<Customer> listOfCustomers = new ArrayList<>(Arrays.asList(new Customer("Eku", "Tamm", "Eku@mail.com", PersonType.ADULT, "A157"),
                new Customer("Mike", "Muscle", "Mike@mail.com", PersonType.ADULT, "A357"),
                new Customer("Mia", "Monroe", "Mia@mail.com", PersonType.ADULT, "A757")));

        when(customerService.getAllCustomers()).thenReturn(listOfCustomers);

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(listOfCustomers.size()));
    }

    @Test
    void shouldUpdateCustomer() throws Exception {

        Customer customer = new Customer("Eku", "Tamm", "Eku@mail.com", PersonType.ADULT, "A157");
        Customer updatedCustomer = new Customer("Eku", "Update", "Eku@mail.com", PersonType.ADULT, "A157");

        when(customerService.getCustomerById(0L)).thenReturn(Optional.of(customer).get());
        when(customerService.updateCustomer(any(Customer.class))).thenReturn(updatedCustomer);


        mockMvc.perform(put("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Eku"));
    }

    @Test
    void shouldDeleteCustomerById() throws Exception {
        long id = 1L;

        doNothing().when(customerService).deleteCustomerById(id);
        mockMvc.perform(delete("/api/users/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteAllCustomers() throws Exception {

        doNothing().when(customerService).deleteAllCustomers();
        mockMvc.perform(delete("/api/users"))
                .andExpect(status().isNoContent());
    }
}
