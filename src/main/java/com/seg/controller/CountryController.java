package com.seg.controller;

import com.seg.model.Country;
import com.seg.repository.CountryRepository;
import com.seg.slugify.TagSlugifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    private CountryRepository countryRepository;

    private TagSlugifier tagSlugifier= new TagSlugifier();

    @Autowired
    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PostMapping
    public ResponseEntity<Country> createCountry (@RequestBody Country country){

            StringBuilder slug = new StringBuilder(tagSlugifier.slugify(country.getName()));
            List<Country> foundCountry = countryRepository.findByTagContains(String.valueOf(slug));
            if(foundCountry.size()>0){
                slug = tagSlugifier.slugify(foundCountry, slug);
            }

            Country country1 = countryRepository.save(new Country(country.getName(),
                    slug.toString()
            ,country.getDescription(),country.getImageUrl()));
            return new ResponseEntity<>(country1, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries (){

            List<Country> allCountries = countryRepository.findAll();
            return new ResponseEntity<>(allCountries,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> findCountryById(@PathVariable long id){

            Optional<Country> foundCountry = countryRepository.findById(id);
            if(foundCountry.isPresent()){
                return new ResponseEntity<>(foundCountry.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<Country> updateCountry (@RequestBody Country country){

            return new ResponseEntity<>(countryRepository.save(country), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCountryById(@PathVariable Long id){

           countryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllCountries(){

            countryRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
