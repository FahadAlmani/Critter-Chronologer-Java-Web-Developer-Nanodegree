package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
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

    public Employee saveEmployee(Employee employee){
        return this.employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long employeeId){
        return this.employeeRepository.getOne(employeeId);
    }
    @Transactional
    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable,  long employeeId){
        Employee employee = this.employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);
    }
    public Set<Employee> getAllEmployeesForService(Set<EmployeeSkill> employeeSkills,LocalDate date){
        Set<Employee> employees = this.employeeRepository.findAllByDaysAvailableAndSkillsIn(date.getDayOfWeek(), employeeSkills);
        return employees.stream().map(employee -> employee).filter(employee -> employee.getSkills().equals(employeeSkills)).collect(Collectors.toSet());
    }
}
