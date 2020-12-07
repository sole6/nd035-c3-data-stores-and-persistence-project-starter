package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.EmployeeService;
import com.udacity.jdnd.course3.critter.Service.PetService;
import com.udacity.jdnd.course3.critter.Service.ScheduleService;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;
    @Autowired
    CustomerService customerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
       Schedule schedule = convertScheduleDTOSchedule(scheduleDTO);
        schedule.setScheduleDate(scheduleDTO.getDate());
        List<Employee> employeeList = new ArrayList<>();
        List<Pet> petList = new ArrayList<>();
        for(Long employeeId: scheduleDTO.getEmployeeIds()){
            Optional<Employee> employee = employeeService.getEmployee(employeeId);
            employeeList.add(employee.get());
        }
        for(Long petId:scheduleDTO.getPetIds()){
            Optional<Pet> pet = petService.getPet(petId);
            if(pet.isPresent()){
                petList.add(pet.get());
            }
        }
        schedule.setEmployee(employeeList);
        schedule.setPets(petList);
       Schedule scheduleSaved = scheduleService.save(schedule);
       ScheduleDTO scheduleDTO1 = convertScheduleScheduleDTO(scheduleSaved);
       List<Long> petIds = new ArrayList<>();
       List<Long> employeeIds = new ArrayList<>();
       for (Employee emp: scheduleSaved.getEmployee()){
           employeeIds.add(emp.getId());
       }
       for (Pet pe: scheduleSaved.getPets()){
            petIds.add(pe.getId());
        }
        scheduleDTO1.setEmployeeIds(employeeIds);
       scheduleDTO1.setPetIds(petIds);
       scheduleDTO1.setDate(schedule.getScheduleDate());

       return scheduleDTO1;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        Iterable<Schedule> scheduleList = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Schedule schedule: scheduleList){
            ScheduleDTO scheduleDTODTO1 = convertScheduleScheduleDTO(schedule);
            scheduleDTODTO1.setDate(schedule.getScheduleDate());
            List <Long> petIds = new ArrayList<>();
            List <Long> employeeIds = new ArrayList<>();
            for(Pet pet : schedule.getPets()){
                petIds.add(pet.getId());
            }
            for (Employee employee:schedule.getEmployee()){
                employeeIds.add(employee.getId());
            }
            scheduleDTODTO1.setPetIds(petIds);
            scheduleDTODTO1.setEmployeeIds(employeeIds);

            scheduleDTOList.add(scheduleDTODTO1);
        }
        return scheduleDTOList;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Optional<Pet> pet = petService.getPet(petId);
        List<Schedule> scheduleList = new ArrayList<>();
        if(pet.isPresent()){
            scheduleList = scheduleService.getScheduleForPet(pet);
        }

        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Schedule schedule: scheduleList){
            ScheduleDTO scheduleDTODTO1 = convertScheduleScheduleDTO(schedule);
            scheduleDTODTO1.setDate(schedule.getScheduleDate());
            List <Long> petIds = new ArrayList<>();
            List <Long> employeeIds = new ArrayList<>();
            for(Pet p : schedule.getPets()){
                petIds.add(p.getId());
            }
            for (Employee emp:schedule.getEmployee()){
                employeeIds.add(emp.getId());
            }
            scheduleDTODTO1.setPetIds(petIds);
            scheduleDTODTO1.setEmployeeIds(employeeIds);

            scheduleDTOList.add(scheduleDTODTO1);
        }
        return scheduleDTOList;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Optional<Employee> employee = employeeService.getEmployee(employeeId);
        List<Schedule> scheduleList = new ArrayList<>();
        if(employee.isPresent()){

            scheduleList = scheduleService.getScheduleForEmployee(employee);
        }
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Schedule schedule: scheduleList){
            ScheduleDTO scheduleDTODTO1 = convertScheduleScheduleDTO(schedule);
            scheduleDTODTO1.setDate(schedule.getScheduleDate());
            List <Long> petIds = new ArrayList<>();
            List <Long> employeeIds = new ArrayList<>();
            for(Pet pet : schedule.getPets()){
                petIds.add(pet.getId());
            }
            for (Employee emp:schedule.getEmployee()){
                employeeIds.add(emp.getId());
            }
            scheduleDTODTO1.setPetIds(petIds);
            scheduleDTODTO1.setEmployeeIds(employeeIds);

            scheduleDTOList.add(scheduleDTODTO1);
        }
        return scheduleDTOList;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Optional<Customer> customer = customerService.findUser(customerId);
        List<Pet> petList = customer.get().getPets();
        List<Schedule> scheduleList = new ArrayList<>();
        for(Pet pet : petList){
            scheduleList.addAll( scheduleService.getScheduleForPet(Optional.ofNullable(pet)));
        }

        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Schedule schedule: scheduleList){
            ScheduleDTO scheduleDTODTO1 = convertScheduleScheduleDTO(schedule);
            scheduleDTODTO1.setDate(schedule.getScheduleDate());
            List <Long> petIds = new ArrayList<>();
            List <Long> employeeIds = new ArrayList<>();
            for(Pet pet : schedule.getPets()){
                petIds.add(pet.getId());
            }
            for (Employee emp:schedule.getEmployee()){
                employeeIds.add(emp.getId());
            }
            scheduleDTODTO1.setPetIds(petIds);
            scheduleDTODTO1.setEmployeeIds(employeeIds);

            scheduleDTOList.add(scheduleDTODTO1);
        }
        return scheduleDTOList;

    }

    private Schedule convertScheduleDTOSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }
    private ScheduleDTO convertScheduleScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        return scheduleDTO;
    }
}
