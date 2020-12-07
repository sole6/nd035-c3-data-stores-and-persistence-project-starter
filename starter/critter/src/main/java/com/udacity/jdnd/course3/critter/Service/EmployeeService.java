package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
    public Optional<Employee> getEmployee (Long employeeId){
        return  employeeRepository.findById(employeeId);
    }
    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> employeeSkills, DayOfWeek dayOfWeek){
        List <Employee> employee1 = employeeRepository.findAllEmployeesByDaysAvailable(dayOfWeek);
        List<Employee> employeeList = new ArrayList<>();
        for(Employee employee2 : employee1){
            if(employee2.getSkills().containsAll(employeeSkills)) {
                employeeList.add(employee2);
            }
        }
        return employeeList;
    }


}
