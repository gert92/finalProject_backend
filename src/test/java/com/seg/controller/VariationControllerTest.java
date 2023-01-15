package com.seg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seg.constants.MealPlans;
import com.seg.model.*;
import com.seg.repository.VariationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(VariationController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class VariationControllerTest {

    @MockBean
    private VariationRepository repository;

    private MockMvc mockMvc;


    @Autowired
    public VariationControllerTest(MockMvc mockMvc){
        this.mockMvc=mockMvc;

    }

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }


    @Test
    void shouldCreateVariationAndReturnIsOkResponse() throws Exception {
        Country country = new Country("Turkey","iiiii","oopospaos","kjksahhs");
        City city = new City("istanbul");
        Hotel hotel = new Hotel("hotel", "some hotel", "hhhggs", "jjjj", country, city, "iiii");
        Date date = new Date(2023, 01, 10);
        Variation variation = new Variation(date, 2, MealPlans.AI
                , hotel
                , new BigDecimal("200.00"), 4);
        when(repository.save(variation)).thenReturn(variation);


        mockMvc.perform(post("/api/variations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(variation))).andDo(print())
                .andExpect(status().isOk()).andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));





    }

    @Test
    void shouldGetAllVariationsAndReturnIsOkResponse()throws Exception {
        Country country = new Country("Turkey","iiiii","oopospaos","kjksahhs");
        City city = new City("istanbul");

        Hotel hotel = new Hotel("hotel", "some hotel", "hhhggs", "jjjj", country, city, "iiii");
        when(repository.findAll()).thenReturn(Arrays.asList(new Variation(new Date(2023 - 02 - 10), 2, MealPlans.AI, hotel, new BigDecimal("200.00"), 4)));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/variations")
                .accept(MediaType.APPLICATION_JSON).content("[" +
                        "{\"startDate\":\"2023-02-10\",\"numberOfNights\":2,\"plan\":\"AI\",\"hotel\":{\"id\":"+hotel.getId()+"},\"price\":200,\"freeSeats\":4 }" +

                        "]");

        mockMvc.perform(request)
                .andExpect(status().isOk());

    }

    @Test
    void shouldUpdateVariationAndReturnIsOkResponse() throws Exception{
        Country country = new Country("Turkey","iiiii","oopospaos","kjksahhs");
        City city = new City("istanbul");


        Hotel hotel = new Hotel("hotel", "some hotel", "hhhggs", "jjjj", country, city, "iiii");
        Variation variation = new Variation(new Date(2023 - 02 - 10), 2, MealPlans.AI, hotel, new BigDecimal("200.00"), 4);
        Variation updatedVariation = new Variation(new Date(2023 - 02 - 10), 3, MealPlans.AI, hotel, new BigDecimal("200.00"), 4);
        hotel.setId(0l);
        when(repository.findById(hotel.getId())).thenReturn(Optional.of(variation));
        when(repository.save(any(Variation.class))).thenReturn(updatedVariation);

        mockMvc.perform(put("/api/variations/{id}",0l)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedVariation)))
                .andExpect(status().isOk());



    }

    @Test
    void shouldDeleteAllVariationsAndReturnNoContentResponse() throws Exception{
        doNothing().when(repository).deleteAll();

        mockMvc.perform(delete("/api/variations")).andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteVariationByIdAndReturnNoContentResponse() throws Exception {
        Long id = 0l;
        doNothing().when(repository).deleteById(id);

        mockMvc.perform(delete("/api/variations/{id}",id)).andExpect(status().isNoContent());
    }
}