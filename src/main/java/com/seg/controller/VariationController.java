package com.seg.controller;

import com.seg.model.Variation;
import com.seg.repository.VariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class VariationController {
    private VariationRepository variationRepository;

    @Autowired
    public VariationController (VariationRepository variationRepository){
        this.variationRepository=variationRepository;
    }

    @PostMapping("packages")
    public ResponseEntity<Variation> createVariation (@RequestBody Variation variation){
        try{
            Variation newVariation = variationRepository.save(new Variation(
                     variation.getStartDate()
                    ,variation.getNumberOfNights()
                    ,variation.getPlan()
                    ,variation.getPrice()
                    ,variation.getFreeSeats()));
            return new ResponseEntity<>(newVariation,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/packages")
    public ResponseEntity<List<Variation>> getAllVariations(){
        try{
            List<Variation> allVariations = new ArrayList<>();
            allVariations=variationRepository.findAll();
            return new ResponseEntity<>(allVariations,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/Packages/{price}")
    public ResponseEntity<List<Variation>> getAllVariationsWithPriceLessThan(@PathVariable double price){
        try{
            List<Variation> allVariationsLessThanPrice=variationRepository.findByPriceLessThan(price);
            return new ResponseEntity<>(allVariationsLessThanPrice,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/Packages/{date}")
    public ResponseEntity<List<Variation>> getAllVariationsAfterDate(@PathVariable Date date){
        try{
            List<Variation> allVariationsAfterDate=variationRepository.findByStartDateIsAfter(date);
            return new ResponseEntity<>(allVariationsAfterDate,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/packages/{id}")
    public ResponseEntity<Variation> updateVariation(@PathVariable long id,@RequestBody Variation variation){
        try{
            Optional<Variation> foundVariation = variationRepository.findById(id);
            if(foundVariation.isPresent()){
                Variation newVariation = foundVariation.get();
                newVariation.setStartDate(variation.getStartDate());
                newVariation.setNumberOfNights(variation.getNumberOfNights());
                newVariation.setPrice(variation.getPrice());
                newVariation.setPlan(variation.getPlan());
                newVariation.setFreeSeats(variation.getFreeSeats());
                return new ResponseEntity<>(variationRepository.save(newVariation),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/packages")
    public ResponseEntity<HttpStatus> deleteAllVariations(){
        try{
            variationRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/packages/{id}")
    public ResponseEntity<HttpStatus> deleteVariationById(@PathVariable long id){
        try{
            variationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
