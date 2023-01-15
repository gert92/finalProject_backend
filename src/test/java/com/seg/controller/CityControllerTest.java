package com.seg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seg.model.City;
import com.seg.repository.CityRepository;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityController.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class CityControllerTest {

    @MockBean
    private CityRepository repository;

    private MockMvc mockMvc;

    @Autowired
    public CityControllerTest(MockMvc mockMvc){
        this.mockMvc=mockMvc;
    }


    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }



    @Test
    void shouldCreateCity() throws Exception {
        City city = new City("istanbul");
        when(repository.save(city)).thenReturn(city);

        mockMvc.perform(post("/api/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(city))).andDo(print())
                .andExpect(status().isCreated()).andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));

    }

    @Test
    void shouldReturnAllCitiesAndResponseIsOK() throws Exception {
        List<City> cities = Arrays.asList(new City("istanbul"));
        when(repository.findAll()).thenReturn(cities);
        mockMvc.perform(get("/api/cities").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(cities))).andDo(print())
                .andExpect(status().isOk()).andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldFindCityByIdAndResponseIsOk() throws Exception {
        City city = new City("istanbul");
        when(repository.findById(0l)).thenReturn(Optional.of(city));
        mockMvc.perform(get("/api/cities/{id}",0l).contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(city))).andDo(print())
                .andExpect(status().isOk()).andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));

    }

    @Test
    void shouldUpdateCityAndResponseIdOk() throws Exception{
        City city = new City("istanbul");
        City updatedCity = new City("ankara");
        when(repository.findById(0l)).thenReturn(Optional.of(city));
        when(repository.save(any(City.class))).thenReturn(updatedCity);
        mockMvc.perform(put("/api/cities/{id}",0l).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedCity))).andDo(print())
                .andExpect(status().isOk()).andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldReturnNoContentResponse() throws Exception{
        doNothing().when(repository).deleteById(0l);

        mockMvc.perform(delete("/api/cities/{id}",0l)).andDo(print())
                .andExpect(status().isNoContent()).andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));

    }

    @Test
    void shouldReturnNoContentResponseForAllCities() throws Exception{
        doNothing().when(repository).deleteAll();

        mockMvc.perform(delete("/api/cities")).andDo(print())
                .andExpect(status().isNoContent()).andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }
}