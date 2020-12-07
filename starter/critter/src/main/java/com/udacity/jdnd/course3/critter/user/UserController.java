package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.EmployeeService;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entities.Pet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertCustomerDTOCustomer(customerDTO);
        Customer customerSaved = customerService.saveCustomer(customer);
        return convertCustomerCustomerDTO(customerSaved);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customerList = (List<Customer>) customerService.getAllCustomers();
        List <CustomerDTO> customerDTOS = new ArrayList<CustomerDTO>();
        List<Long> petIds = new ArrayList<Long>();
        for (Customer customer: customerList){
            CustomerDTO customerDTO2 = convertCustomerCustomerDTO(customer);

            for(Pet pet:customer.getPets()){
                petIds.add(pet.getId());
            }
            customerDTO2.setPetIds(petIds);
            customerDTOS.add(customerDTO2);
        }

        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getOwnerByPet(petId);
        CustomerDTO customerDTO = convertCustomerCustomerDTO(customer);
        List<Pet> petList = customer.getPets();
        List<Long> petIds = new ArrayList<Long>();
        for (Pet pet:petList){
            petIds.add(pet.getId());
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee= convertEmployeeDTOCEmployee(employeeDTO);
        return convertEmployeeEmployeeDTO(employeeService.saveEmployee(employee));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Optional<Employee> employee = employeeService.getEmployee(employeeId);
        EmployeeDTO employeeDTO = new EmployeeDTO();
        if(employee.isPresent()){
            employeeDTO= convertEmployeeEmployeeDTO(employee.get());
        }
        return employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {

        List <Employee> employeeList = employeeService.findEmployeesForService(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek());
        List <EmployeeDTO> employeeDTOList = new ArrayList<EmployeeDTO>();
        for (Employee employee : employeeList){
            employeeDTOList.add(convertEmployeeEmployeeDTO(employee));
        }
        return  employeeDTOList;
    }
    private CustomerDTO convertCustomerCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }
    private Customer convertCustomerDTOCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
    private EmployeeDTO convertEmployeeEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }
    private Employee convertEmployeeDTOCEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

}
