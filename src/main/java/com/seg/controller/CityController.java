package com.seg.controller;

import com.seg.model.City;
import com.seg.model.Country;
import com.seg.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CityController {

    private CityRepository cityRepository;

    @Autowired
    public CityController(CityRepository cityRepository){
        this.cityRepository=cityRepository;
    }

    @PostMapping("/cities")
    public ResponseEntity<City> createCity (@RequestBody City city){
        try{
            City city1 = cityRepository.save(new City(city.getName()));
            return new ResponseEntity<>(city1, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getAllCities (){
        try {
            List<City> allCities = cityRepository.findAll();
            return new ResponseEntity<>(allCities,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<City> findCityById(@PathVariable long id){
        try{
            Optional<City> foundCity = cityRepository.findById(id);
            if(foundCity.isPresent()){
                return new ResponseEntity<>(foundCity.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/cities/{id}")
    public ResponseEntity<City> updateCity (@PathVariable long id, @RequestBody City city){
        try{
            Optional<City> city1 = cityRepository.findById(id);
            if(city1.isPresent()){
                City foundCountry = city1.get();
                foundCountry.setName(city.getName());
                return new ResponseEntity<>(cityRepository.save(foundCountry),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cities/{id}")
    public ResponseEntity<HttpStatus> deleteCityById(@PathVariable Long id){
        try{
            cityRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cities")
    public ResponseEntity<HttpStatus> deleteAllCities(){
        try{
            cityRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
