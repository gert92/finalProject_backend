package com.seg.service;


import com.seg.model.Customer;

import java.util.List;

public interface UserService {

    List<Customer> getAllUsers();

    Customer saveUser(Customer customer);

    Customer getUserById(Long id);

    Customer updateUser(Customer customer);

    void deleteUserById(Long id);
}
