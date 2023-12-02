package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService {
    @Autowired
    private PetRepository petRepository;

    public Pet savePet(Pet pet){
        Pet newPet = this.petRepository.save(pet);

        Customer customer = newPet.getCustomer();
        customer.getPets().add(newPet);

        return newPet;
    }

    public List<Pet> getAllPets(){
        return this.petRepository.findAll();
    }

    public Pet getPetById(Long petId){
        return this.petRepository.getOne(petId);
    }

    public List<Pet> getAllPetsByOwnerId(Long ownerId){
        return this.petRepository.findAllByCustomerId(ownerId);
    }

    public List<Pet> getPetsByListOfId(List<Long> petsId){
        return this.petRepository.findAllById(petsId);
    }

}
