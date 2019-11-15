package com.bank.Entities;

import com.bank.Service.FinancialProductService;
import com.bank.Service.Promotion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class CheckingAccount extends  FinancialProduct {

    private double balance;
    private List<Transaction> checkingAccountHistory;
    private boolean eligibleForPromotion;



    public CheckingAccount(double balance) {
        this.balance = balance;
        this.checkingAccountHistory = new ArrayList<>();
        this.eligibleForPromotion = false;
    }

    public double getBalance() {
        return balance;
    }


}
