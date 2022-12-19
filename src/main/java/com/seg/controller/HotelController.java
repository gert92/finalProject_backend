package com.seg.controller;

import com.seg.model.Hotel;
import com.seg.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HotelController {
    private HotelRepository hotelRepository;

    @Autowired
    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }
    
    @GetMapping("hotels")
    public ResponseEntity<List<Hotel>> getAllHotels(@RequestParam String hotelName){
        try{
            List<Hotel> allHotels = new ArrayList<>();
            if(hotelName==null){
                hotelRepository.findAll().forEach(h->allHotels.add(h));
            }else{
                hotelRepository.findByName(hotelName).forEach(h->allHotels.add(h));
            }
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
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        try{
            Hotel newHotel = hotelRepository.save(new Hotel(hotel.getName()
                    ,hotel.getTag(),hotel.getCountry(),hotel.getCity()
                    ,hotel.getPackages()));
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
                oldHotel.setCountry(hotel.getCountry());
                oldHotel.setCity(hotel.getCity());
                oldHotel.setPackages(hotel.getPackages());

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
