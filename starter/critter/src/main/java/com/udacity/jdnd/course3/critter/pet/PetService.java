package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    public Pet savePet(Pet pet){
        return this.petRepository.save(pet);
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
