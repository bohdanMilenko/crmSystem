package com.bank.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private boolean isCanadianResident;
    private List<RelativeType> relativeTypes = new ArrayList<>();
    private boolean isStudent;


    public Customer(String name, String surname, LocalDate dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
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

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudentAccount(){
        this.isStudent = true;
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

    @Override
    protected Customer clone() throws CloneNotSupportedException {
        Customer clone = (Customer) super.clone();
        return clone;
    }


}
