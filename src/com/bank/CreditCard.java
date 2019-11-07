package com.bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class CreditCard extends FinancialProduct {

    private double balance;
    private  double creditLimit;
    private int overLimitCount;
    private boolean defaulted;

    private List<Transaction> creditCardTransactions;

    static final int LOWEST_THRESHOLD = 1000;
    static final int MIDDLE_THRESHOLD = 5000;
    static final int TOP_THRESHOLD = 10000;
    private static final double OVER_LIMIT_FEE = 29.99;

    public CreditCard(double creditLimit) {
        this.balance+= WELCOMING_BONUS;
        this.creditLimit = creditLimit * (-1);
        this.defaulted = false;
        this.creditCardTransactions = new ArrayList<>();
    }

    @Override
    void depositMoneyToAccount(double incomingTransaction) {
        balance+= incomingTransaction;
        creditCardTransactions.add(super.createTransaction(incomingTransaction));
    }

    @Override
    void withdrawMoneyFromAccount(double outgoingTransaction) {
        if(creditLimit >= (balance - outgoingTransaction)){
            overLimitCount++;
            System.out.println("You used more funds than you credit line allows you!");
            creditCardTransactions.add(super.createTransaction(OVER_LIMIT_FEE));
        }

        balance -= outgoingTransaction;
        creditCardTransactions.add(super.createTransaction(outgoingTransaction));
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

    public List<Transaction> getCreditCardTransactions() {
        return Collections.unmodifiableList(creditCardTransactions);
    }
}
