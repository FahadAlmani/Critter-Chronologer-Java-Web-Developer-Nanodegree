package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PetRepository petRepository;

    public UserService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerById(Long customerId){
        return this.customerRepository.getOne(customerId);
    }

    public Customer saveCustomer(Customer customer){
        return this.customerRepository.save(customer);
    }

    public List<Customer> getAllCustomer(){
        return this.customerRepository.findAll();
    }

    public Customer getCustomerByPet(Long petId){
        return this.customerRepository.findByPets(this.petRepository.getOne(petId));
    }
}
