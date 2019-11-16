package com.bank.Entities;

import java.util.ArrayList;
import java.util.List;


public class CreditCard  extends  FinancialProduct{

    private double balance;
    private  double creditLimit;
    private int overLimitCount;
    private boolean defaulted;

    private List<Transaction> creditCardTransactions;

    public static final int LOWEST_THRESHOLD = 1000;
    public static final int MIDDLE_THRESHOLD = 5000;
    public static final int TOP_THRESHOLD = 10000;
    private static final double OVER_LIMIT_FEE = -29.99;
    static final int WELCOMING_BONUS = 50;

    public CreditCard(double creditLimit) {
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



}