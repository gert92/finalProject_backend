package com.seg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seg.constants.PersonType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(CustomerController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }


    @Test
    void shouldCreateCustomer() throws Exception {
        Customer customer = new Customer("Eku", "Tamm", "Eku@mail.com", PersonType.ADULT, "A157");

        when(customerService.saveCustomer(customer)).thenReturn(customer);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldReturnCustomerById() throws Exception {
        Customer customer = new Customer("Eku", "Tamm", "Eku@mail.com", PersonType.ADULT, "A157");

        when(customerService.getCustomerById(0L)).thenReturn(customer);

        mockMvc.perform(get("/api/customers/{id}", 0L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Eku"))
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldReturnAllCustomers() throws Exception {
        List<Customer> listOfCustomers = new ArrayList<>(Arrays.asList(new Customer("Eku", "Tamm", "Eku@mail.com", PersonType.ADULT, "A157"),
                new Customer("Mike", "Muscle", "Mike@mail.com", PersonType.ADULT, "A357"),
                new Customer("Mia", "Monroe", "Mia@mail.com", PersonType.ADULT, "A757")));

        when(customerService.getAllCustomers()).thenReturn(listOfCustomers);

        mockMvc.perform(get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(listOfCustomers.size()))
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldUpdateCustomer() throws Exception {

        Customer customer = new Customer("Eku", "Tamm", "Eku@mail.com", PersonType.ADULT, "A157");
        Customer updatedCustomer = new Customer("Eku", "Update", "Eku@mail.com", PersonType.ADULT, "A157");

        when(customerService.getCustomerById(0L)).thenReturn(customer);
        when(customerService.updateCustomer(any(Customer.class))).thenReturn(updatedCustomer);


        mockMvc.perform(put("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Update"))
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldDeleteCustomerById() throws Exception {
        long id = 1L;

        doNothing().when(customerService).deleteCustomerById(id);
        mockMvc.perform(delete("/api/customers/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldDeleteAllCustomers() throws Exception {

        doNothing().when(customerService).deleteAllCustomers();
        mockMvc.perform(delete("/api/customers"))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }
}
