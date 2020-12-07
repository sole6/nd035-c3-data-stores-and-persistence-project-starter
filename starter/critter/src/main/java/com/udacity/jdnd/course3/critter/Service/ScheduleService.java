package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Iterable<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }
    public List<Schedule> getScheduleForEmployee(Optional<Employee> employee){
            return scheduleRepository.findAllByEmployee(employee);
    }

    public List<Schedule> getScheduleForPet(Optional<Pet> pet) {
        return scheduleRepository.findAllByPets(pet);
    }

}
