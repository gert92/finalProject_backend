package com.seg.controller;

import com.seg.model.Trip;
import com.seg.repository.TripRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TripController {

    private TripRepository tripRepository;

    @Autowired
    public TripController(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @GetMapping("/trips")
    public ResponseEntity<List<Trip>> getAllTrips(){
        try {
            List<Trip> listOfTrips = tripRepository.findAll();
            return new ResponseEntity<>(listOfTrips, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/trips/{id}")
    public ResponseEntity<Trip> getTripsById(@PathVariable long id){
        try{
            Optional<Trip> tripById = tripRepository.findById(id);
            if(tripById.isPresent()){
                return new ResponseEntity<>(tripById.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/trips")
    public ResponseEntity<Trip> createTrip(@Valid @RequestBody Trip trip) {
        try {
            Trip myTrip = tripRepository.save(new Trip(
                    trip.getUser(),
                    trip.getPackageVariation(),
                    trip.getDepartureDate(),
                    trip.getReturnDate(),
                    trip.getHotel(),
                    trip.getAdults(),
                    trip.getChilds()));
            return new ResponseEntity<>(myTrip, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/trips/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable long id,@Valid @RequestBody Trip newTrip){
        try{
            Optional<Trip> foundTrip = tripRepository.findById(id);
            if(foundTrip.isPresent()){
                Trip oldTrip = foundTrip.get();
                oldTrip.setUser(newTrip.getUser());
                oldTrip.setPackageVariation(newTrip.getPackageVariation());
                oldTrip.setDepartureDate(newTrip.getDepartureDate());
                oldTrip.setReturnDate(newTrip.getReturnDate());
                oldTrip.setHotel(newTrip.getHotel());
                oldTrip.setAdults(newTrip.getAdults());
                oldTrip.setChilds(newTrip.getChilds());
                return new ResponseEntity<>(tripRepository.save(oldTrip),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/trips/{id}")
    public ResponseEntity<HttpStatus> deleteTripById(@PathVariable("id") long id) {
        try {
            tripRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users")
    public ResponseEntity<HttpStatus> deleteAllTrips() {
        try {
            tripRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
