package com.seg.service.impl;

import com.seg.model.Country;
import com.seg.model.Hotel;
import com.seg.model.Trip;
import com.seg.model.Variation;
import com.seg.repository.CountryRepository;
import com.seg.repository.HotelRepository;
import com.seg.repository.TripRepository;
import com.seg.repository.VariationRepository;
import com.seg.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TripServiceImpl implements TripService {

    private TripRepository tripRepository;
    private HotelRepository hotelRepository;
    private CountryRepository countryRepository;
    private VariationRepository variationRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository,VariationRepository variationRepository) {
        this.tripRepository = tripRepository;
        this.variationRepository=variationRepository;
    }

    @Override
    public ResponseEntity<List<Trip>> getAllTrips() {

            return  new ResponseEntity<>(tripRepository.findAll(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Trip> saveTrip(Trip trip) {

            Trip myTrip = tripRepository.save(trip);

            /*myTrip.getPackageVariation()
                    .setFreeSeats(myTrip.getPackageVariation().getFreeSeats()-myTrip.getAdults()-myTrip.getChildren());
            variationRepository.save(myTrip.getPackageVariation());

            Hotel hotelWithUpdatedCounter = trip.getHotel();
            hotelWithUpdatedCounter.setCounter(hotelWithUpdatedCounter.getCounter()+1);

            Country countryWithUpdatedCounter = trip.getHotel().getCountry();
            countryWithUpdatedCounter.setCounter(countryWithUpdatedCounter.getCounter()+1);

             */

            return new ResponseEntity<>(trip,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Trip> getTripById(Long id) {

            Optional<Trip> foundTrip =  tripRepository.findById(id);
            if(foundTrip.isPresent()){
                return new ResponseEntity<>(foundTrip.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Trip> updateTrip(Long id,Trip trip) {

            Optional<Trip> foundTrip = tripRepository.findById(id);

            Trip oldTrip= new Trip();
            if (foundTrip.isPresent()) {
                oldTrip = foundTrip.get();
                oldTrip.setCustomer(trip.getCustomer());
                oldTrip.setPackageVariation(trip.getPackageVariation());
                oldTrip.setDepartureDate(trip.getDepartureDate());
                oldTrip.setReturnDate(trip.getReturnDate());
                oldTrip.setHotel(trip.getHotel());
                oldTrip.setAdults(trip.getAdults());
                oldTrip.setChildren(trip.getChildren());
                tripRepository.save(oldTrip);
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
                return new ResponseEntity<>(oldTrip,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteTrip(Trip trip) {

            tripRepository.delete(trip);
            Variation variation = new Variation();
            variation=trip.getPackageVariation();
            int updatedNumberOfAvailableSeats = variation.getFreeSeats()+trip.getAdults()+trip.getChildren();
            variation.setFreeSeats(updatedNumberOfAvailableSeats);
            variationRepository.save(variation);

            Hotel hotelWithUpdatedCounter = trip.getHotel();
            hotelWithUpdatedCounter.setCounter(hotelWithUpdatedCounter.getCounter()-1);

            Country countryWithUpdatedCounter = trip.getHotel().getCountry();
            countryWithUpdatedCounter.setCounter(countryWithUpdatedCounter.getCounter()-1);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @Override
    public ResponseEntity<HttpStatus> deleteTrips() {
            tripRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
