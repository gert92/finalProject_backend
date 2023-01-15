package com.seg.service.impl;

import com.seg.exception.UserNotFoundException;
import com.seg.model.Country;
import com.seg.model.Hotel;
import com.seg.model.Trip;
import com.seg.model.Variation;
import com.seg.repository.CountryRepository;
import com.seg.repository.HotelRepository;
import com.seg.repository.TripRepository;
import com.seg.repository.VariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TripService {

    private TripRepository tripRepository;
    private HotelRepository hotelRepository;
    private CountryRepository countryRepository;
    private VariationRepository variationRepository;

    @Autowired
    public TripService(TripRepository tripRepository, VariationRepository variationRepository) {
        this.tripRepository = tripRepository;
        this.variationRepository=variationRepository;
    }


    public List<Trip> getAllTrips() {
            return  tripRepository.findAll();
    }


    public Trip saveTrip(Trip trip) {

//           Trip myTrip = tripRepository.save(trip);

            /*
            myTrip.getPackageVariation()
                    .setFreeSeats(myTrip.getPackageVariation().getFreeSeats()-myTrip.getAdults()-myTrip.getChildren());
            variationRepository.save(myTrip.getPackageVariation());

            Hotel hotelWithUpdatedCounter = trip.getHotel();
            hotelWithUpdatedCounter.setCounter(hotelWithUpdatedCounter.getCounter()+1);

            Country countryWithUpdatedCounter = trip.getHotel().getCountry();
            countryWithUpdatedCounter.setCounter(countryWithUpdatedCounter.getCounter()+1);

             */

            return tripRepository.save(trip);
    }


    public Trip getTripById(Long id) {

        return tripRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user by ID: " + id));
    }


    public Trip updateTrip(Trip trip) {

            Optional<Trip> foundTrip = tripRepository.findById(trip.getId());
            if (foundTrip.isPresent()) {
                tripRepository.save(trip);
            }
            Variation variationWithNewFreeSeats =new Variation();
            if(trip.getAdults()+trip.getChildren()!= foundTrip.get().getAdults()+ foundTrip.get().getChildren()){
                int changeInNumberOfPassengers =(trip.getAdults()+trip.getChildren())-(foundTrip.get().getAdults()+ foundTrip.get().getChildren());
                variationWithNewFreeSeats= trip.getPackageVariation();
                if(changeInNumberOfPassengers <0){
                    variationWithNewFreeSeats.setFreeSeats(variationWithNewFreeSeats.getFreeSeats()+ changeInNumberOfPassengers);
                }else{
                    variationWithNewFreeSeats.setFreeSeats(variationWithNewFreeSeats.getFreeSeats()- changeInNumberOfPassengers);
                }
                variationRepository.save(variationWithNewFreeSeats);
                return trip;
            }
        throw new UserNotFoundException("No user by ID: " + trip.getId());
    }


    public void deleteTripById(Long id) {

//            tripRepository.delete(trip);
//            Variation variation = new Variation();
//            variation=trip.getPackageVariation();
//            int updatedNumberOfAvailableSeats = variation.getFreeSeats()+trip.getAdults()+trip.getChildren();
//            variation.setFreeSeats(updatedNumberOfAvailableSeats);
//            variationRepository.save(variation);
//
//            Hotel hotelWithUpdatedCounter = trip.getHotel();
//            hotelWithUpdatedCounter.setCounter(hotelWithUpdatedCounter.getCounter()-1);
//
//            Country countryWithUpdatedCounter = trip.getHotel().getCountry();
//            countryWithUpdatedCounter.setCounter(countryWithUpdatedCounter.getCounter()-1);

        tripRepository.deleteById(id);

    }


    public void deleteAllTrips() {
            tripRepository.deleteAll();

    }
}
