package com.bank;

import java.util.ArrayList;
import java.util.List;

public class Customer extends FinancialDetails {

    private String name;
    private String surname;
    private int age;
    private double balance;

    private List<BankProducts> bankProducts;
    private List<Relatives> relatives;

    public Customer(String name, String surname, int age, double balance, List<BankProducts> bankProducts, List<Relatives> relatives) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.balance = balance;
        this.bankProducts = bankProducts;
        this.relatives = relatives;
    }

    public Customer(String name, String surname, int age, double balance){
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.bankProducts = new ArrayList<>();
        this.relatives = new ArrayList<>();

    }

    @Override
    double getBalance() {
        return balance;
    }
}
