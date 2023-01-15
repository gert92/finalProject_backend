package com.seg.controller;

import com.seg.model.Hotel;
import com.seg.repository.HotelRepository;
import com.seg.slugify.TagSlugifier;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    private HotelRepository hotelRepository;

    private TagSlugifier tagSlugifier = new TagSlugifier();


    @Autowired
    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;


    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels(@RequestParam(required = false) String search) {
            List<Hotel> allHotels;
            if (search == null){
                allHotels = hotelRepository.findAll();
            } else {
                allHotels = hotelRepository.findByNameContainingIgnoreCaseOrCountryNameContainingIgnoreCaseOrCityNameContainingIgnoreCase(search, search, search);
            }
            if (allHotels.isEmpty()) {
                return new ResponseEntity<>(allHotels,HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(allHotels, HttpStatus.OK);

    }

    @GetMapping("/{slug}")
    public ResponseEntity<Hotel> findHotelBySlug(@PathVariable("slug") String slug) {

            Optional<Hotel> foundHotel = hotelRepository.findHotelByTag(slug);
            if (foundHotel.isPresent()) {

                return new ResponseEntity<>(foundHotel.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody @Valid Hotel hotel) {

        StringBuilder slug = new StringBuilder(tagSlugifier.slugify(hotel.getName()));
        List<Hotel> foundHotel = hotelRepository.findByTagContains(String.valueOf(slug));
        if (foundHotel.size() > 0) {
            slug = tagSlugifier.slugify(foundHotel, slug);
        }
        Hotel newHotel = hotelRepository.save(new Hotel(
                hotel.getName()
                , hotel.getDescription()
                , slug.toString()
                , hotel.getNovatoursKey()
                , hotel.getCountry()
                , hotel.getCity()
                , hotel.getImageUrl()));
        return new ResponseEntity<>(newHotel, HttpStatus.CREATED);

    }


    @PutMapping
    public ResponseEntity<Hotel> updateHotel(@RequestBody Hotel hotel) {

            return new ResponseEntity<>(hotelRepository.save(hotel), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteHotelById(@PathVariable Long id) {
            hotelRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllHotels() {

            hotelRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
