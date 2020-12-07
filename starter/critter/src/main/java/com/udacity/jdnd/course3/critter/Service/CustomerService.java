package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetService petService;

    public CustomerService() {
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Iterable<Customer> getAllCustomers (){
        return customerRepository.findAll();
    }
    public Optional<Customer> findUser (Long customerId){
        return customerRepository.findById(customerId);
    }

    public Customer getOwnerByPet(Long petID){
        Optional<Pet> pet = petService.getPet(petID);
        Iterable<Customer> custome2=customerRepository.findAll();
        Customer customer = null;
        if(pet.isPresent()){
            customer = customerRepository.findById(pet.get().getCustomer().getId()).get();
        }
        return customer;
    }
}
