package com.bank;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private boolean isCanadianResident;
    private List<RelativeType> relativeTypes;


    public Customer(String name, String surname, LocalDate dateOfBirth, boolean isCanadianResident) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.isCanadianResident = isCanadianResident;
        this.relativeTypes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean isCanadianResident() {
        return isCanadianResident;
    }

    public List<RelativeType> getRelativeTypes() {
        return relativeTypes;
    }



    public enum RelativeType {
        PARENT,
        SIBLING,
        PARENTSSIBLING,
        GRANDPARENT;
    }

    public enum GENDER {
        MALE,
        FEMALE,
        UNKNOWN;
    }



}
