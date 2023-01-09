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
@RequestMapping("/api/users")
public class CustomerController {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllUsers(){

            List<Customer> customerList = customerRepository.findAll();
            return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getUsersById(@PathVariable long id){

            Optional<Customer> userById = customerRepository.findById(id);
            if(userById.isPresent()){
                return new ResponseEntity<>(userById.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {

            Customer myCustomer = customerRepository.save(new Customer(customer.getFirstName(),
                                                       customer.getLastName(),
                                                       customer.getEmail(),
                                                       customer.getPersonType(),
                                                       customer.getPassportNumber()));
            return new ResponseEntity<>(myCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateUser(@PathVariable long id, @Valid @RequestBody Customer newCustomer){

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

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable long id) {

            customerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllUsers() {

            customerRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

