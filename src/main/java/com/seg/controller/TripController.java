package com.seg.controller;

import com.seg.model.Trip;
import com.seg.service.impl.TripService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {


    private TripService tripService;

    @Autowired
    public TripController( TripService tripService) {
        this.tripService= tripService;
    }

    @GetMapping
    public ResponseEntity<List<Trip>> findAllTrips(){

        List<Trip> customerList = tripService.getAllTrips();
        return new ResponseEntity<>(customerList, HttpStatus.OK);

        }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> findTripById(@PathVariable long id) {

        return new ResponseEntity<>(tripService.getTripById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Trip> addTrip(@Valid @RequestBody Trip trip) {

        return new ResponseEntity<>(tripService.saveTrip(trip), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Trip> updateTrip(@Valid @RequestBody Trip trip) {

        return new ResponseEntity<>(tripService.updateTrip(trip), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTripById(@PathVariable long id) {

         tripService.deleteTripById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteAllTrips() {
        tripService.deleteAllTrips();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
