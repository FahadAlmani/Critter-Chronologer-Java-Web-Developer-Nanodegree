package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetService petService;

    @Autowired
    UserService userService;

    public Schedule saveSchedule(Schedule schedule){
        return  this.scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules(){
        return this.scheduleRepository.findAll();
    }

    public List<Schedule> getAllScheduleForPet(Long petId){
        List<Schedule> schedules = this.scheduleRepository.findByPetList(this.petService.getPetById(petId));
        return schedules;
    }

    public List<Schedule> getAllScheduleForEmployee(Long employeeId){
        List<Schedule> schedules = this.scheduleRepository.findByEmployeeList(this.userService.getEmployeeById(employeeId));
        return schedules;
    }

    public List<Schedule> getAllScheduleForCustomer(Long customerId){
        List<Pet> pets = this.userService.getCustomerById(customerId).getPets();
        List<Schedule> customerSchedule = new ArrayList<>();
        for(Pet pet : pets){
            customerSchedule.addAll(this.scheduleRepository.findByPetList(pet));
        }
        return customerSchedule;
    }

}
