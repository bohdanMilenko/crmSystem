package com.bank.entities;

import java.util.ArrayList;
import java.util.List;

public class CheckingAccount extends  FinancialProduct implements  Cloneable {

    private double balance;
    private List<Transaction> checkingAccountHistory = new ArrayList<>();
    private boolean eligibleForPromotion = false;
    public static final int PROMOTION_ELIGIBLE_EXPENSES = -5000;


    public CheckingAccount(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getCheckingAccountHistory() {
        return new ArrayList<>(checkingAccountHistory);
    }

    public boolean isEligibleForPromotion() {
        return eligibleForPromotion;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addTransactionToTransactionHistory(Transaction transaction) {
        checkingAccountHistory.add(transaction);
    }

    public void setEligibleForPromotion(boolean eligibleForPromotion) {
        this.eligibleForPromotion = eligibleForPromotion;
    }

}
