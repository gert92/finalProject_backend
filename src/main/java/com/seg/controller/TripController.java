package com.seg.controller;

import com.seg.model.Trip;
import com.seg.service.TripService;
import com.seg.service.impl.TripServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TripController {


    private TripService tripService;

    @Autowired
    public TripController( TripServiceImpl tripService) {
        this.tripService= tripService;
    }

    @GetMapping("/trips")
    public ResponseEntity<List<Trip>> getAllTrips(){
        return tripService.getAllTrips();
    }

    @GetMapping("/trips/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable long id){
        return tripService.getTripById(id);
    }

    @PostMapping("/trips")
    public ResponseEntity<Trip> createTrip(@Valid @RequestBody Trip trip) {
        return tripService.saveTrip(trip);
    }

    @PutMapping("/trips/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable long id,@Valid @RequestBody Trip newTrip) {
        return tripService.updateTrip(id, newTrip);
    }

    @DeleteMapping("/trips")
    public ResponseEntity<HttpStatus> deleteTripById(@RequestBody Trip trip) {
        return tripService.deleteTrip(trip);
    }



    /**@DeleteMapping("/trips")
    public ResponseEntity<HttpStatus> deleteAllTrips() {
       return tripService.deleteTrips();
    }
    **/
}
