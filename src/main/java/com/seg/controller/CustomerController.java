package com.seg.controller;

import com.seg.model.Customer;
import com.seg.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<Customer>> getAllUsers(){
        try {
            List<Customer> customerList = customerRepository.findAll();
            return new ResponseEntity<>(customerList, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Customer> getUsersById(@PathVariable long id){
        try{
            Optional<Customer> userById = customerRepository.findById(id);
            if(userById.isPresent()){
                return new ResponseEntity<>(userById.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        try {
            Customer myCustomer = customerRepository.save(new Customer(customer.getFirstName(),
                                                       customer.getLastName(),
                                                       customer.getEmail(),
                                                       customer.getPersonType(),
                                                       customer.getPassportNumber()));
            return new ResponseEntity<>(myCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Customer> updateUser(@PathVariable long id, @Valid @RequestBody Customer newCustomer){
        try{
            Optional<Customer> foundUser = customerRepository.findById(id);
            if(foundUser.isPresent()){
                Customer oldCustomer = foundUser.get();
                oldCustomer.setFirstName(newCustomer.getFirstName());
                oldCustomer.setLastName(newCustomer.getLastName());
                oldCustomer.setEmail(newCustomer.getEmail());
                oldCustomer.setPersonType(newCustomer.getPersonType());
                oldCustomer.setPassportNumber(newCustomer.getPassportNumber());
                return new ResponseEntity<>(customerRepository.save(oldCustomer),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable long id) {
        try {
            customerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            customerRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

