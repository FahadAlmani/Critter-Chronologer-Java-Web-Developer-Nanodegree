package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    UserService userService;
    @Autowired
    PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = this.petService.savePet(this.convertDTOToPet(petDTO));
        return this.convertPetToDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = this.petService.getPetById(petId);
        return this.convertPetToDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = this.petService.getAllPets();
        return pets.stream().map(pet -> this.convertPetToDTO(pet)).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = this.petService.getAllPetsByOwnerId(ownerId);
        return pets.stream().map(pet -> convertPetToDTO(pet)).collect(Collectors.toList());
    }

    private Pet convertDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);

        Long customerId = petDTO.getOwnerId();

        if(customerId != null){
            Customer customer = this.userService.getCustomerById(customerId);
            pet.setCustomer(customer);
        }
        return pet;
    }

    private PetDTO convertPetToDTO(Pet pet){
        PetDTO  petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);

        Customer customer = pet.getCustomer();

        if(customer != null){
            petDTO.setOwnerId(customer.getId());
        }
        return petDTO;
    }
}
