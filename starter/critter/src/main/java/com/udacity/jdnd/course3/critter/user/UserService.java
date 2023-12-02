package com.udacity.jdnd.course3.critter.user;


import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PetService petService;

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
        return this.customerRepository.findByPets(this.petService.getPetById(petId));
    }

    public Employee saveEmployee(Employee employee){
        return this.employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long employeeId){
        return this.employeeRepository.getOne(employeeId);
    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable,  long employeeId){
        Employee employee = this.employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);
    }
    public List<Employee> getAllEmployeesForService(Set<EmployeeSkill> employeeSkills,LocalDate date){
        Set<Employee> employees = this.employeeRepository.findAllByDaysAvailableAndSkillsIn(date.getDayOfWeek(), employeeSkills);
        List<Employee> matchEmployees = employees.stream().filter(employee -> employee.getSkills().containsAll(employeeSkills)).collect(Collectors.toList());
        return matchEmployees;
    }
}
