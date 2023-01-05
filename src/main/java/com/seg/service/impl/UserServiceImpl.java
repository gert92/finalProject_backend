package com.seg.service.impl;

import com.seg.exception.UserNotFoundException;
import com.seg.model.Customer;
import com.seg.repository.UserRepository;
import com.seg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Customer> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Customer saveUser(Customer customer) {
        return userRepository.save(customer);
    }

    @Override
    public Customer getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user by ID: " + id));
    }

    @Override
    public Customer updateUser(Customer customer) {
        return userRepository.save(customer);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
