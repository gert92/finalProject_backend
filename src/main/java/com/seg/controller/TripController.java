package com.seg.controller;

import com.seg.model.Trip;
import com.seg.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TripController {

    private TripRepository tripRepository;

    @Autowired
    public TripController(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }




}
