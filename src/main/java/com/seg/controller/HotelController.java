package com.seg.controller;

import com.seg.model.Country;
import com.seg.model.Hotel;
import com.seg.repository.CityRepository;
import com.seg.repository.CountryRepository;
import com.seg.repository.HotelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000")
public class HotelController {
    private HotelRepository hotelRepository;


    @Autowired
    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;

    }
    
    @GetMapping("hotels")
    public ResponseEntity<List<Hotel>> getAllHotels(){
        try{
            List<Hotel> allHotels = new ArrayList<>();

                hotelRepository.findAll().forEach(h->allHotels.add(h));

            if(allHotels.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(allHotels, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        
    }
    
    @GetMapping("/hotel/{id}")
    public ResponseEntity<Hotel> findHotelById(@PathVariable("id")Long id){
        try{
            Optional<Hotel> foundHotel = hotelRepository.findById(id);
            if(foundHotel.isPresent()){
                return new ResponseEntity<>(foundHotel.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @PostMapping("/hotels")
    public ResponseEntity<Hotel> createHotel(@RequestBody @Valid Hotel hotel){
        try{
            Hotel newHotel = hotelRepository.save(new Hotel(hotel.getName(), hotel.getDescription()
                    ,hotel.getTag(),hotel.getCountry(),hotel.getCity()
                    ,hotel.getImage()));
            return new ResponseEntity<>(newHotel,HttpStatus.CREATED);

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/hotels/{id}")
    public ResponseEntity<Hotel> updateHotel(@RequestBody Hotel hotel,@PathVariable Long id){
        try{
            Optional<Hotel> foundHotel = hotelRepository.findById(id);
            Hotel oldHotel = new Hotel();
            if (foundHotel.isPresent()){
                 oldHotel =foundHotel.get();
                oldHotel.setName(hotel.getName());
                oldHotel.setTag(hotel.getTag());
                oldHotel.setCountry(hotel.getCountry());
                oldHotel.setCity(hotel.getCity());
                oldHotel.setVariations(hotel.getVariations());
                oldHotel.setCounter(hotel.getCounter());
                oldHotel.setImage(hotel.getImage());

            }
            return new ResponseEntity<>(hotelRepository.save(oldHotel),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<HttpStatus> deleteHotelById(@PathVariable Long id){
        try{
            hotelRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/hotels/")
    public ResponseEntity<HttpStatus> deleteAllHotels(){
        try{
            hotelRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
        

    
}
