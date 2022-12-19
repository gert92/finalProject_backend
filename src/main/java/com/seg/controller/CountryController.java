package com.seg.controller;

import com.seg.model.Country;
import com.seg.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CountryController {
    private CountryRepository countryRepository;

    @Autowired
    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PostMapping("/countries")
    public ResponseEntity<Country> createCountry (@RequestBody Country country){
        try{
            Country country1 = countryRepository.save(new Country(country.getName()));
            return new ResponseEntity<>(country1, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getAllCountries (){
        try {
            List<Country> allCountries = countryRepository.findAll();
            return new ResponseEntity<>(allCountries,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/countries/{id}")
    public ResponseEntity<Country> findCountryById(@PathVariable long id){
        try{
            Optional<Country> foundCountry = countryRepository.findById(id);
            if(foundCountry.isPresent()){
                return new ResponseEntity<>(foundCountry.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<Country> updateCountry (@PathVariable long id, @RequestBody Country country){
        try{
            Optional<Country> country1 = countryRepository.findById(id);
            if(country1.isPresent()){
                Country foundCountry = country1.get();
                foundCountry.setName(country.getName());
                return new ResponseEntity<>(countryRepository.save(foundCountry),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<HttpStatus> deleteCountryById(@PathVariable Long id){
        try{
           countryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/countries")
    public ResponseEntity<HttpStatus> deleteAllCountries(){
        try{
            countryRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
