package com.seg.service;

import com.seg.model.Trip;

import java.util.List;

public interface TripService {

    List<Trip> getAllTrips();

    Trip saveTrip(Trip trip);

    Trip getTripById(Long id);

    Trip updateTrip(Trip trip);

    void deleteTripById(Long id);
}
