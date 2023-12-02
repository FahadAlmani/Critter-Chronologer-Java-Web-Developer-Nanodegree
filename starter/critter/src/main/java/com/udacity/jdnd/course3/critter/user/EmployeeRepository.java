package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.DayOfWeek;

import java.util.Set;
@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Set<Employee> findAllByDaysAvailableAndSkillsIn(DayOfWeek dayOfWeek,Set<EmployeeSkill> employeeSkillSet);
}
