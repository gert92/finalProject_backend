package com.seg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seg.model.City;
import com.seg.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityController.class)
class CityControllerTest {

    @MockBean
    private CityRepository repository;

    private MockMvc mockMvc;

    @Autowired
    public CityControllerTest(MockMvc mockMvc){
        this.mockMvc=mockMvc;
    }



    @Test
    void shouldCreateCity() throws Exception {
        City city = new City("istanbul");
        when(repository.save(city)).thenReturn(city);

        mockMvc.perform(post("/api/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(city)))
                .andExpect(status().isCreated());

    }

    @Test
    void getAllCities() {
    }

    @Test
    void findCityById() {
    }

    @Test
    void updateCity() {
    }

    @Test
    void deleteCityById() {
    }

    @Test
    void deleteAllCities() {
    }
}