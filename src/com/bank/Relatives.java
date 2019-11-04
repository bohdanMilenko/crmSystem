package com.bank;

public class Relatives {

    private String name;
    private String surname;
    private int age;
    private RelativeType relativeType;
    private SexType sexType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public RelativeType getRelativeType() {
        return relativeType;
    }

    public void setRelativeType(RelativeType relativeType) {
        this.relativeType = relativeType;
    }

    public SexType getSexType() {
        return sexType;
    }

    public void setSexType(SexType sexType) {
        this.sexType = sexType;
    }
}
