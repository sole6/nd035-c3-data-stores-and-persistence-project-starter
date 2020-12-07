package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.PetService;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;
    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Customer customer = customerService.findUser(petDTO.getOwnerId()).get();
        Pet pet = convertPetDTOPet(petDTO);
        customer.getPets().add(pet);
        pet.setCustomer(customer);
        Pet petSaved = petService.savePet(pet);
        PetDTO petDTO1 = convertPetPetDTO(petSaved);
        petDTO1.setOwnerId(petSaved.getCustomer().getId());
        return petDTO1;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Optional<Pet> pet = petService.getPet(petId);
        PetDTO petDTO = new PetDTO();
        if(pet.isPresent()){
            petDTO = convertPetPetDTO(pet.get());
            petDTO.setOwnerId(pet.get().getCustomer().getId());
        }
        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer customer = customerService.findUser(ownerId).get();
        List <Pet> petList =  petService.getPetsByOwner(ownerId);
        List <PetDTO> petDTO = new ArrayList<PetDTO>();
        for (Pet pet: petList){
            PetDTO petDTO1 = convertPetPetDTO(pet);
            petDTO1.setOwnerId(pet.getCustomer().getId());
            petDTO.add(petDTO1);
        }
        return petDTO;
    }
    private Pet convertPetDTOPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
    private PetDTO convertPetPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        return petDTO;
    }
}
