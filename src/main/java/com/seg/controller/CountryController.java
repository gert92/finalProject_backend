package com.seg.controller;

import com.seg.model.Country;
import com.seg.model.Hotel;
import com.seg.repository.CountryRepository;
import com.seg.slugify.TagSlugifier;
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

    private TagSlugifier tagSlugifier= new TagSlugifier();

    @Autowired
    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PostMapping("/countries")
    public ResponseEntity<Country> createCountry (@RequestBody Country country){

            StringBuilder slug = new StringBuilder(tagSlugifier.slugify(country.getName()));
            List<Country> foundCountry = countryRepository.findByTagContains(String.valueOf(slug));
            if(foundCountry.size()>0){
                slug = tagSlugifier.slugify(foundCountry, slug);
            }

            Country country1 = countryRepository.save(new Country(country.getName(),
                    slug.toString()
            ,country.getDescription(),country.getImage()));
            return new ResponseEntity<>(country1, HttpStatus.CREATED);

    }

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getAllCountries (){

            List<Country> allCountries = countryRepository.findAll();
            return new ResponseEntity<>(allCountries,HttpStatus.OK);
    }

    @GetMapping("/countries/{id}")
    public ResponseEntity<Country> findCountryById(@PathVariable long id){

            Optional<Country> foundCountry = countryRepository.findById(id);
            if(foundCountry.isPresent()){
                return new ResponseEntity<>(foundCountry.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<Country> updateCountry (@PathVariable long id, @RequestBody Country country){
            Optional<Country> country1 = countryRepository.findById(id);
            if(country1.isPresent()){
                Country foundCountry = country1.get();
                foundCountry.setName(country.getName());
                foundCountry.setDescription(country.getDescription());
                foundCountry.setHotels(country.getHotels());
                foundCountry.setCounter(country1.get().getCounter());
                return new ResponseEntity<>(countryRepository.save(foundCountry),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<HttpStatus> deleteCountryById(@PathVariable Long id){

           countryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/countries")
    public ResponseEntity<HttpStatus> deleteAllCountries(){

            countryRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
