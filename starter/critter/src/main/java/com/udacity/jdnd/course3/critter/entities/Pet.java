package com.udacity.jdnd.course3.critter.entities;

import javax.persistence.*;

@Entity
public class Pet {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    Customer owner;

    public Customer getCustomer() {
        return owner;
    }

    public void setCustomer(Customer owner) {
        this.owner = owner;
    }

    @Id
    @GeneratedValue
    long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    String petType;
}
