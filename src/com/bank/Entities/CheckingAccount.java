package com.bank.Entities;

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

    public List<Transaction> getCheckingAccountHistory() {
        return checkingAccountHistory;
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
