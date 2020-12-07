package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {
   // List<Pet> findPetsByOwner( Customer owner);

    List<Pet> findPetsByOwner(Optional<Customer> customer);
}
