package com.seg.service;

import com.seg.model.Trip;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TripService {

    ResponseEntity<List<Trip>> getAllTrips();

    ResponseEntity<Trip> saveTrip (Trip trip)throws Exception;

    ResponseEntity<Trip> getTripById(Long id) throws Exception;

    ResponseEntity<Trip> updateTrip(Long id,Trip trip) throws Exception;

    ResponseEntity<HttpStatus> deleteTrip(Trip trip);

    ResponseEntity<HttpStatus> deleteTrips();
}
