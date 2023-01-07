package com.seg.service.impl;

import com.seg.exception.UserNotFoundException;
import com.seg.model.Customer;
import com.seg.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository userRepository) {
        this.customerRepository = userRepository;
    }


    public Customer saveCustomer(Customer customer) {
        Customer myCustomer = customerRepository.save(new Customer(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPersonType(),
                customer.getPassportNumber()));
        return customerRepository.save(myCustomer);
    }

    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user by ID: " + id));
    }


    public Customer updateCustomer(Customer customer){

        Optional<Customer> foundUser = customerRepository.findById(customer.getId());
        if (foundUser.isPresent()) {
            Customer oldUser = foundUser.get();
            oldUser.setFirstName(customer.getFirstName());
            oldUser.setLastName(customer.getLastName());
            oldUser.setEmail(customer.getEmail());
            oldUser.setPersonType(customer.getPersonType());
            oldUser.setPassportNumber(customer.getPassportNumber());
            return customerRepository.save(oldUser);
        }
        throw new UserNotFoundException("No user by ID: " + customer.getId());
    }


    public void deleteCustomerById(Long id) {

        customerRepository.deleteById(id);
    }

    public void deleteAllCustomers() {

        customerRepository.deleteAll();
    }
}
