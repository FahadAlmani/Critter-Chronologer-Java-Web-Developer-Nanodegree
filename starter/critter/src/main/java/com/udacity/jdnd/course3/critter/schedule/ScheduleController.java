package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
@Autowired
private ScheduleService scheduleService;

@Autowired
private UserService userService;

@Autowired
private PetService petService;
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = this.scheduleService.saveSchedule(this.convertDTOToSchedule(scheduleDTO));
        return this.convertScheduleToDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List< Schedule> schedules = this.scheduleService.getAllSchedules();
        return schedules.stream().map(schedule -> this.convertScheduleToDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List< Schedule> schedules = this.scheduleService.getAllScheduleForPet(petId);
        return schedules.stream().map(schedule -> this.convertScheduleToDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List< Schedule> schedules = this.scheduleService.getAllScheduleForEmployee(employeeId);
        return schedules.stream().map(schedule -> this.convertScheduleToDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List< Schedule> schedules = this.scheduleService.getAllScheduleForCustomer(customerId);
        return schedules.stream().map(schedule -> this.convertScheduleToDTO(schedule)).collect(Collectors.toList());
    }

    private Schedule convertDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        if(employeeIds != null){
            List<Employee> employees = employeeIds
                    .stream()
                    .map(employeeId -> this.userService.getEmployeeById(employeeId))
                    .collect(Collectors.toList());
            schedule.setEmployeeList(employees);
        }

        List<Long> petIds = scheduleDTO.getPetIds();
        if(petIds != null){
            List<Pet> pets = petIds
                    .stream()
                    .map(petId -> this.petService.getPetById(petId))
                    .collect(Collectors.toList());
            schedule.setPetList(pets);
        }

        return schedule;
    }

    private ScheduleDTO convertScheduleToDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Employee> employees = schedule.getEmployeeList();
        if(employees != null){
            List<Long> employeeIds = employees
                    .stream()
                    .map(employee -> employee.getId())
                    .collect(Collectors.toList());
            scheduleDTO.setEmployeeIds(employeeIds);
        }

        List<Pet> pets = schedule.getPetList();
        if(pets != null){
            List<Long> petIds = pets
                    .stream()
                    .map(pet -> pet.getId())
                    .collect(Collectors.toList());
            scheduleDTO.setPetIds(petIds);
        }

        return scheduleDTO;
    }
}
