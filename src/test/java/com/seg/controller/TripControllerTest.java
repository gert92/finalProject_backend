package com.seg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seg.constants.MealPlans;
import com.seg.constants.PersonType;
import com.seg.model.*;
import com.seg.service.impl.TripService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(TripController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripService tripService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }


    @Test
    void shouldCreateTrip() throws Exception {

        Trip myTrip = new Trip(new Customer("Eku", "Tamm", "Eku@mail.com", PersonType.ADULT, "A157"),
                new Variation(new Date(2023 - 01 - 10), 2, MealPlans.AI,
                        new Hotel("Grand Kolibri Prestige & Spa", "nice hotel", "", "5231",
                                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                                new City("Payallar"), "Kolibri.img"), new BigDecimal("200.00"), 4),
                new Date(2023 - 01 - 20), new Date(2023 - 01 - 27), new Hotel("Grand Kolibri Prestige & Spa", "nice hotel", "", "5231",
                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                new City("Payallar"), "Kolibri.img"), 2, 2);

        when(tripService.saveTrip(myTrip)).thenReturn(myTrip);

        mockMvc.perform(post("/api/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(myTrip)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

    }

    @Test
    void shouldReturnTripById() throws Exception {
        long id = 1L;

        Trip myTrip = new Trip(new Customer("Eku", "Tamm", "Eku@mail.com", PersonType.ADULT, "A157"),
                new Variation(new Date(2023 - 01 - 10), 2, MealPlans.AI,
                        new Hotel("Grand Kolibri Prestige & Spa", "nice hotel", "", "5231",
                                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                                new City("Payallar"), "Kolibri.img"), new BigDecimal("200.00"), 4),
                new Date(2023 - 01 - 20), new Date(2023 - 01 - 27), new Hotel("Grand Kolibri Prestige & Spa", "nice hotel", "", "5231",
                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                new City("Payallar"), "Kolibri.img"), 2, 2);

        when(tripService.getTripById(id)).thenReturn(myTrip);

        mockMvc.perform(get("/api/trips/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adults").value(2))
                .andDo(print())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldReturnAllTrips() throws Exception {
        List<Trip> listOfTrips = new ArrayList<>(Arrays.asList(new Trip(new Customer("Eku", "Tamm", "Eku@mail.com", PersonType.ADULT, "A157"),
                new Variation(new Date(2023 - 01 - 10), 2, MealPlans.AI,
                        new Hotel("Grand Kolibri Prestige & Spa", "nice hotel", "", "5231",
                                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                                new City("Payallar"), "Kolibri.img"), new BigDecimal("200.00"), 4),
                new Date(2023 - 01 - 20), new Date(2023 - 01 - 27), new Hotel("Grand Kolibri Prestige & Spa", "nice hotel", "", "5231",
                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                new City("Payallar"), "Kolibri.img"), 2, 2),
                new Trip(new Customer("Mike", "White", "Mike@mail.com", PersonType.ADULT, "A757"),
                        new Variation(new Date(2023 - 01 - 9), 3, MealPlans.AI,
                                new Hotel("Grand Kolibri Prestige & Spa", "nice hotel", "", "5551",
                                        new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                                        new City("Payallar"), "Kolibri.img"), new BigDecimal("200.00"), 4),
                        new Date(2023 - 01 - 19), new Date(2023 - 01 - 26), new Hotel("Grand Kolibri Prestige & Spa", "nice hotel", "", "5231",
                        new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                        new City("Payallar"), "Kolibri.img"), 4, 2)));

        when(tripService.getAllTrips()).thenReturn(listOfTrips);

        mockMvc.perform(get("/api/trips")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(listOfTrips.size()))
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldUpdateTrip() throws Exception {

        Trip myTrip = new Trip(new Customer("Eku", "Tamm", "Eku@mail.com", PersonType.ADULT, "A157"),
                new Variation(new Date(2023 - 01 - 10), 2, MealPlans.AI,
                        new Hotel("Grand Kolibri Prestige & Spa", "nice hotel", "", "5231",
                                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                                new City("Payallar"), "Kolibri.img"), new BigDecimal("200.00"), 4),
                new Date(2023 - 01 - 20), new Date(2023 - 01 - 27), new Hotel("Grand Kolibri Prestige & Spa", "nice hotel", "", "5231",
                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                new City("Payallar"), "Kolibri.img"), 2, 2);

        Trip myUpdatedTrip = new Trip(new Customer("Eku", "Tamm", "Eku@mail.com", PersonType.ADULT, "A157"),
                new Variation(new Date(2023 - 01 - 10), 2, MealPlans.AI,
                        new Hotel("Grand Kolibri Prestige & Spa", "nice hotel", "", "5231",
                                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                                new City("Payallar"), "Kolibri.img"), new BigDecimal("200.00"), 4),
                new Date(2023 - 01 - 20), new Date(2023 - 01 - 27), new Hotel("Grand Kolibri Prestige & Spa", "nice hotel", "", "5231",
                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                new City("Payallar"), "Kolibri.img"), 4, 4);


        when(tripService.getTripById(0L)).thenReturn(myTrip);
        when(tripService.updateTrip(any(Trip.class))).thenReturn(myUpdatedTrip);


        mockMvc.perform(put("/api/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(myUpdatedTrip)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adults").value(4))
                .andExpect(jsonPath("$.children").value(4))
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));

    }

    @Test
    void shouldDeleteTripById() throws Exception {
        long id = 1L;

        doNothing().when(tripService).deleteTripById(id);
        mockMvc.perform(delete("/api/trips/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldDeleteAllHotels() throws Exception {

        doNothing().when(tripService).deleteAllTrips();
        mockMvc.perform(delete("/api/trips"))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }
}
