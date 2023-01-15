package com.seg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seg.constants.MealPlans;
import com.seg.model.City;
import com.seg.model.Country;
import com.seg.model.Hotel;
import com.seg.model.Variation;
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
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(HotelController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class HotelControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelRepository hotelRepository;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }


    @Test
    void shouldCreateHotel() throws Exception {
        Hotel hotel = new Hotel("Grand Kolibri Prestige & Spa", "nice hotel", "", "5231",
                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                new City("Payallar"), "Kolibri.img");

        when(hotelRepository.save(hotel)).thenReturn(hotel);

        mockMvc.perform(post("/api/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(hotel)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));

    }

    @Test
    void shouldReturnHotelByTag() throws Exception {
        Hotel hotel = new Hotel("Grand Kolibri Prestige & Spa", "nice hotel", "", "5231",
                new Country("Türkiye", "grand-kolibri-prestige-spa", "Nice view", "Türkiye.img"),
                new City("Payallar"), "Kolibri.img");

        when(hotelRepository.findHotelByTag("grand-kolibri-prestige-spa")).thenReturn(Optional.of(hotel));

        mockMvc.perform(get("/api/hotels/{slug}", "grand-kolibri-prestige-spa")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Grand Kolibri Prestige & Spa"))
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldReturnAllHotels() throws Exception {
        List<Hotel> listOfHotels = new ArrayList<>(Arrays.asList(new Hotel("Grand Kolibri Prestige & Spa", "nice hotel", "", "5231",
                        new Country("Türkiye", "grand-kolibri-prestige-spa", "Nice view", "Türkiye.img"),
                        new City("Payallar"), "Kolibri.img"),
                new Hotel("Villa Augusto", "villa-augusto", "", "5551",
                        new Country("Türkiye", "grand-kolibri-prestige-spa", "Sunny", "Augusto.img"),
                        new City("Payallar"), "Villa.img"),
                new Hotel("Club Sun Heaven", "amazing gardens", "", "5331",
                        new Country("Türkiye", "club-sun-heaven", "Nice view", "Türkiye.img"),
                        new City("Payallar"), "Sun.img")));

        when(hotelRepository.findAll()).thenReturn(listOfHotels);

        mockMvc.perform(get("/api/hotels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(listOfHotels.size()))
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldUpdateHotel() throws Exception {

        Hotel hotel = new Hotel("Grand Kolibri Prestige & Spa", "nice hotel", "", "5231",
                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                new City("Payallar"), "Kolibri.img");

        Hotel updatedHotel = new Hotel("Grand Kolibri Prestige & Spa", "many pools", "", "5231",
                new Country("Türkiye", "", "Nice view", "Türkiye.img"),
                new City("Payallar"), "Kolibri.img");

        when(hotelRepository.findHotelByTag("")).thenReturn(Optional.of(hotel));
        when(hotelRepository.save(ArgumentMatchers.any(Hotel.class))).thenReturn(updatedHotel);


        mockMvc.perform(put("/api/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedHotel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("many pools"))
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));

    }

    @Test
    void shouldDeleteHotelById() throws Exception {
        long id = 1L;

        doNothing().when(hotelRepository).deleteById(id);
        mockMvc.perform(delete("/api/hotels/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }

    @Test
    void shouldDeleteAllHotels() throws Exception {

        doNothing().when(hotelRepository).deleteAll();
        mockMvc.perform(delete("/api/hotels"))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("{methodName}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint())));
    }
}
