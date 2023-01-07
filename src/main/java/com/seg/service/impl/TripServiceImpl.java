package com.seg.service.impl;

import com.seg.model.Trip;
import com.seg.model.Variation;
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
    private VariationRepository variationRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository,VariationRepository variationRepository) {
        this.tripRepository = tripRepository;
        this.variationRepository=variationRepository;
    }

    @Override
    public ResponseEntity<List<Trip>> getAllTrips() {
        try {
            return  new ResponseEntity<>(tripRepository.findAll(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Trip> saveTrip(Trip trip) {
        try{
            Trip myTrip = tripRepository.save(new Trip(
                    trip.getCustomer(),
                    trip.getPackageVariation(),
                    trip.getDepartureDate(),
                    trip.getReturnDate(),
                    trip.getHotel(),
                    trip.getAdults(),
                    trip.getChildren()));

            myTrip.getPackageVariation()
                    .setFreeSeats(myTrip.getPackageVariation().getFreeSeats()-myTrip.getAdults()-myTrip.getChildren());
            variationRepository.save(myTrip.getPackageVariation());
            return new ResponseEntity<>(trip,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

    @Override
    public ResponseEntity<Trip> getTripById(Long id) {
        try{
            Optional<Trip> foundTrip =  tripRepository.findById(id);
            if(foundTrip.isPresent()){
                return new ResponseEntity<>(foundTrip.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    public ResponseEntity<Trip> updateTrip(Long id,Trip trip) {
        try{
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


        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deleteTrip(Trip trip) {
        try {
            tripRepository.delete(trip);
            Variation variation = new Variation();
            variation=trip.getPackageVariation();
            int updatedNumberOfAvailableSeats = variation.getFreeSeats()+trip.getAdults()+trip.getChildren();
            variation.setFreeSeats(updatedNumberOfAvailableSeats);
            variationRepository.save(variation);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<HttpStatus> deleteTrips() {
        try{
            tripRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
