package com.seg.controller;

import com.seg.model.Country;
import com.seg.model.User;
import com.seg.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        try {
            List<User> userList = userRepository.findAll();
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUsersById(@PathVariable long id){
        try{
            Optional<User> userById = userRepository.findById(id);
            if(userById.isPresent()){
                return new ResponseEntity<>(userById.get(),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            User myUser = userRepository.save(new User(user.getFirstName(),
                                                       user.getLastName(),
                                                       user.getEmail(),
                                                       user.getPersonType(),
                                                       user.getPassportNumber()));
            return new ResponseEntity<>(myUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id,@Valid @RequestBody User newUser){
        try{
            Optional<User> foundUser = userRepository.findById(id);
            if(foundUser.isPresent()){
                User oldUser = foundUser.get();
                oldUser.setFirstName(newUser.getFirstName());
                oldUser.setLastName(newUser.getLastName());
                oldUser.setEmail(newUser.getEmail());
                oldUser.setPersonType(newUser.getPersonType());
                oldUser.setPassportNumber(newUser.getPassportNumber());
                return new ResponseEntity<>(userRepository.save(oldUser),HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable("id") long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users")
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
