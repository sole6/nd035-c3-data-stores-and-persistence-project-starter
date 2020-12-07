package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.entities.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List <Employee> findAllEmployeesByDaysAvailable (DayOfWeek dayOfWeek);
}
