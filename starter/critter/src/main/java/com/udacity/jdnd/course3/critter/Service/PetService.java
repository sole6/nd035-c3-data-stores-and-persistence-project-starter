package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    PetRepository petsRepository;
    @Autowired
    CustomerRepository customerRepository;

    public Pet savePet (Pet pet){

        return petsRepository.save(pet);
    }

    public Optional<Pet> getPet (long petId){
        return petsRepository.findById(petId);
    }
    public List<Pet> getPetsByOwner(long ownerId){
        Optional<Customer> customer = customerRepository.findById(ownerId);
        List<Pet> petList = new ArrayList<Pet>();
        if(customer.isPresent()){
            petList = (List<Pet>) petsRepository.findPetsByOwner(customer);
        }
      //  List<Pet> petList = (List<Pet>) petsRepository.findAll();
        return petList;
    }
}
