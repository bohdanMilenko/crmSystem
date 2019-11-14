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
    private boolean isStudent;


    public Customer(String name, String surname, LocalDate dateOfBirth, boolean isCanadianResident, boolean isStudent) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.isCanadianResident = isCanadianResident;
        this.relativeTypes = new ArrayList<>();
        this.isStudent = isStudent;
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

    public boolean isStudent() {
        return isStudent;
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
