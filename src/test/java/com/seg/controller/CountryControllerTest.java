package com.seg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seg.model.City;
import com.seg.model.Country;
import com.seg.model.Hotel;
import com.seg.repository.CountryRepository;
import com.seg.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(CountryController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class CountryControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryRepository countryRepository;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void shouldCreateCountry() throws Exception {

             Country country = new Country("Türkiye", "", "Nice view", "Türkiye.img");


        when(countryRepository.save(country)).thenReturn(country);

        mockMvc.perform(post("/api/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(country)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));

    }

    @Test
    void shouldReturnCountryById() throws Exception {

        long id = 1L;

        Country country = new Country("Türkiye", "", "Nice view", "Türkiye.img");

        when(countryRepository.findById(id)).thenReturn(Optional.of(country));

        mockMvc.perform(get("/api/countries/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Türkiye"))
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldReturnAllCountries() throws Exception {
        List<Country> listOfCountries = new ArrayList<>(Arrays.asList(new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                new Country("Greece", "", "Many pools", "Greece.img"),
                new Country("Italy", "", "Nice view", "Italy.img")));

        when(countryRepository.findAll()).thenReturn(listOfCountries);

        mockMvc.perform(get("/api/countries")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(listOfCountries.size()))
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldUpdateCountry() throws Exception {

        long id = 1L;

        Country country = new Country("Türkiye", "", "Nice view", "Türkiye.img");
        Country updatedCountry = new Country("Türkiye", "", "Many pools", "Türkiye.img");

        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
        when(countryRepository.save(ArgumentMatchers.any(Country.class))).thenReturn(updatedCountry);


        mockMvc.perform(put("/api/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedCountry)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Many pools"))
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));

    }

    @Test
    void shouldDeleteCountryId() throws Exception {
        long id = 1L;

        doNothing().when(countryRepository).deleteById(id);
        mockMvc.perform(delete("/api/countries/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldDeleteAllCountries() throws Exception {

        doNothing().when(countryRepository).deleteAll();
        mockMvc.perform(delete("/api/countries"))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }
}
