package com.bank.entities;

import java.util.ArrayList;
import java.util.List;


public class CreditLine extends  FinancialProduct{

    private double balance;
    private  double creditLimit;
    private int overLimitCount;
    private boolean defaulted;

    private List<Transaction> creditCardTransactions;

    private static final int WELCOMING_BONUS = 50;

    public CreditLine(double creditLimit) {
        this.balance+= WELCOMING_BONUS;
        this.creditLimit = creditLimit * (-1);
        this.defaulted = false;
        this.creditCardTransactions = new ArrayList<>();
        this.overLimitCount = 0;
    }


    public double getBalance() {
        return balance;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public int getOverLimitCount() {
        return overLimitCount;
    }

    public boolean isDefaulted() {
        return defaulted;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void increaseOverLimitCount() {
        this.overLimitCount++;
    }

    public void setDefaulted(boolean defaulted) {
        this.defaulted = defaulted;
    }

    public void addTransactionToTransactionHistory(Transaction transaction) {
        creditCardTransactions.add(transaction);
    }

    public List<Transaction> getCreditCardTransactions() {
        return new ArrayList<>(creditCardTransactions);
    }

    @Override
    protected CreditLine clone() throws CloneNotSupportedException {
        CreditLine clone = (CreditLine) super.clone();
        clone.creditCardTransactions = new ArrayList<>(creditCardTransactions);
        return clone;
    }
}
